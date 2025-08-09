package com.onlinebookstore.book_service.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(property = "inventoryId", generator = ObjectIdGenerators.PropertyGenerator.class)
public class InventoryDTO {

    private int inventoryId;

    @NotBlank(message = "Stock value is required")
    @PositiveOrZero(message = "Stock quantity should be 0 or greater than 0")
    private long stock;

    private BookDTO bookDetails;
}

