package com.onlinebookstore.base_domain.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEvents {

    private int orderId;
    private int bookId;
    private long quantity;
    private String eventType; // ORDER-CREATED, INVENTORY_CONFIRMED, INVENTORY_FAILED
}
