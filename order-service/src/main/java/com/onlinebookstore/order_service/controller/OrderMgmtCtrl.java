package com.onlinebookstore.order_service.controller;

import com.onlinebookstore.order_service.dto.OrderRequestDTO;
import com.onlinebookstore.order_service.entity.Order;
import com.onlinebookstore.order_service.services.OrderMgmtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderMgmtCtrl {

    private final OrderMgmtService orderMgmtService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMgmtCtrl.class);

    @Autowired
    public OrderMgmtCtrl(OrderMgmtService orderMgmtService) {
        this.orderMgmtService = orderMgmtService;
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestBody OrderRequestDTO orderRequest){
        LOGGER.info("Creating order => {}", orderRequest.toString());

        return orderMgmtService.createOrder(orderRequest);
    }

    @GetMapping("/order-details/{orderId}")
    public ResponseEntity<Order> fetchOrderDetailsById(@PathVariable int orderId){
        LOGGER.info("Fetching order details for order id: {}", orderId);

        Order order = orderMgmtService.fetchOrderDetailsById(orderId);
        return ResponseEntity.ok(order);
    }
}
