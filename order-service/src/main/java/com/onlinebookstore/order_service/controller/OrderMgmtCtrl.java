package com.onlinebookstore.order_service.controller;

import com.onlinebookstore.order_service.dto.OrderRequestDTO;
import com.onlinebookstore.order_service.entity.Order;
import com.onlinebookstore.order_service.services.OrderMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderMgmtCtrl {

    private final OrderMgmtService orderMgmtService;

    @Autowired
    public OrderMgmtCtrl(OrderMgmtService orderMgmtService) {
        this.orderMgmtService = orderMgmtService;
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestBody OrderRequestDTO orderRequest){
        return orderMgmtService.createOrder(orderRequest);
    }

    @GetMapping("/order-details/{orderId}")
    public ResponseEntity<Order> fetchOrderDetailsById(@PathVariable int orderId){
        Order order = orderMgmtService.fetchOrderDetailsById(orderId);
        return ResponseEntity.ok(order);
    }
}
