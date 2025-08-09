package com.onlinebookstore.base_domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvents {

    private int orderId;
    private int bookId;
    private long quantity;
    private String eventType; // ORDER-CREATED, INVENTORY_CONFIRMED, INVENTORY_FAILED
}
