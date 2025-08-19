package com.onlinebookstore.order_service.services;

import com.onlinebookstore.base_domain.book.dto.BookDTO;
import com.onlinebookstore.base_domain.events.OrderEvents;
import com.onlinebookstore.order_service.dto.OrderRequestDTO;
import com.onlinebookstore.order_service.entity.Order;
import com.onlinebookstore.order_service.repository.OrderMgmtRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderMgmtService {

    private final OrderMgmtRepo orderMgmtRepo;
    private final BookServiceClient bookServiceClient;
    private final KafkaTemplate<String, OrderEvents> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMgmtService.class);


    @Value("${spring.kafka.topic.order-events}")
    private String orderEventTopic;

    public OrderMgmtService(OrderMgmtRepo orderMgmtRepo, BookServiceClient bookServiceClient, KafkaTemplate<String, OrderEvents> kafkaTemplate) {
        this.orderMgmtRepo = orderMgmtRepo;
        this.bookServiceClient = bookServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    //PRODUCER of Order Event
    public String createOrder(OrderRequestDTO orderRequest){

        ResponseEntity<BookDTO> response = bookServiceClient.fetchBookDetailsById(orderRequest.getBookId());
        BookDTO bookDTO = response.getBody();
        double billAmt = bookDTO.getPrice() * orderRequest.getQuantity();

        LocalDateTime currentTime = LocalDateTime.now();
        Order newOrder = Order.builder()
                .bookId(orderRequest.getBookId())
                .quantity(orderRequest.getQuantity())
                .status("CREATED")
                .orderTimestamp(currentTime)
                .totalBillAmt(billAmt)
                .build();

        Order orderSaved = orderMgmtRepo.save(newOrder);
        LOGGER.info("Order created => {}", orderSaved.toString());

        OrderEvents orderEvent = new OrderEvents(newOrder.getOrderId(), newOrder.getBookId(), newOrder.getQuantity(),"ORDER-CREATED");

        LOGGER.info("Sending order CREATED response => {}", orderEvent.toString());

        kafkaTemplate.send(orderEventTopic, orderEvent)
                .whenComplete((result, ex) -> {
                    if(ex == null){
                        LOGGER.info("OK created Status => {}",  result.getRecordMetadata());
                    }
                    else {
                        ex.printStackTrace();
                        Throwable root = org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(ex);
                        LOGGER.error(String.valueOf(root));
                    }
                });

        return "Order Created";
    }

    public Order fetchOrderDetailsById(int orderId){
        return orderMgmtRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order Id: "+orderId+ " not found.."));
    }

    @KafkaListener(topics = "${spring.kafka.topic.inventory-response}", groupId = "order-group")
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

            LOGGER.info("Updating ORDER status: {}", order.toString());
            orderMgmtRepo.save(order);
        }
    }
}