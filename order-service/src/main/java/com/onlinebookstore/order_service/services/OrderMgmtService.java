package com.onlinebookstore.order_service.services;

import com.onlinebookstore.base_domain.book.dto.BookDTO;
import com.onlinebookstore.base_domain.events.OrderEvents;
import com.onlinebookstore.order_service.dto.OrderRequestDTO;
import com.onlinebookstore.order_service.entity.Order;
import com.onlinebookstore.order_service.repository.OrderMgmtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderMgmtService {

    private final OrderMgmtRepo orderMgmtRepo;

    @Autowired
    private BookServiceClient bookServiceClient;

    @Autowired
    private KafkaTemplate<String, OrderEvents> kafkaTemplate;

    @Autowired
    public OrderMgmtService(OrderMgmtRepo orderMgmtRepo) {
        this.orderMgmtRepo = orderMgmtRepo;
    }

    //PRODUCER of Order Event
    public String createOrder(OrderRequestDTO orderRequest){

        BookDTO bookDTO = bookServiceClient.fetchBookDetailsById(orderRequest.getBookId());
        double billAmt = bookDTO.getPrice() * orderRequest.getQuantity();

        LocalDateTime currentTime = LocalDateTime.now();
        Order newOrder = Order.builder()
                .bookId(orderRequest.getBookId())
                .quantity(orderRequest.getQuantity())
                .status("CREATED")
                .orderTimestamp(currentTime)
                .totalBillAmt(billAmt)
                .build();

        orderMgmtRepo.save(newOrder);

        OrderEvents orderEvent = new OrderEvents(newOrder.getOrderId(), newOrder.getBookId(), newOrder.getQuantity(),"ORDER-CREATED");
        kafkaTemplate.send("order-events", orderEvent);

        return "Order Created";
    }

    public Order fetchOrderDetailsById(int orderId){
        return orderMgmtRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order Id: "+orderId+ " not found.."));
    }

    @KafkaListener(topics = "inventory-response", groupId = "order-group")
    public void updateOrderStatus(OrderEvents orderEvent){
        String eventStatus = orderEvent.getEventType();
        Order order = orderMgmtRepo.findById(orderEvent.getOrderId()).orElse(null);
        LocalDateTime currentTime = LocalDateTime.now();

        if(order != null){
            if("INVENTORY-CONFIRMED".equals(eventStatus)){
                order.setStatus("CONFIRMED");
                order.setOrderTimestamp(currentTime);
            }
            else if("INVENTORY-FAILED".equals(eventStatus)){
                order.setStatus("FAILED");
                order.setOrderTimestamp(currentTime);
            }
            orderMgmtRepo.save(order);
        }
    }
}