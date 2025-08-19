package com.onlinebookstore.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order_details")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private int bookId;
    private String status; // CREATED, CONFIRMED, FAILED
    private long quantity;
    private LocalDateTime orderTimestamp;
    private double totalBillAmt;
}
