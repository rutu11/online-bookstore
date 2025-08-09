package com.onlinebookstore.order_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private int bookId;
    private String status; // CREATED, CONFIRMED, FAILED
    private long quantity;
    private LocalDateTime orderTimestamp;
    private double totalBillAmt;

//    private int userId;
//    private int paymentId;
}
