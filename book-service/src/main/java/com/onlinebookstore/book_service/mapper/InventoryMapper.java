package com.onlinebookstore.book_service.mapper;

import com.onlinebookstore.book_service.dto.BookDTO;
import com.onlinebookstore.book_service.dto.InventoryDTO;
import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.entity.Inventory;

import java.util.List;

public class InventoryMapper {
    public static Inventory toEntity(InventoryDTO dto){
        if(dto == null) return null;

        Book bookEntity = BookMapper.toEntity(dto.getBookDetails());

        return Inventory.builder()
                .stock(dto.getStock())
                .bookDetails(bookEntity)
                .build();
    }

    public static InventoryDTO toDTO(Inventory entity){
        if(entity == null) return null;

        BookDTO bookDto = BookMapper.toDTO(entity.getBookDetails());

        InventoryDTO inventory = new InventoryDTO();
        inventory.setInventoryId(entity.getInventoryId());
        inventory.setStock(entity.getStock());
        inventory.setBookDetails(bookDto);

        return inventory;
    }

    public static List<InventoryDTO> toDTOList(List<Inventory> inventoryList){
        return inventoryList.stream().map(InventoryMapper::toDTO).toList();
    }

    public static List<Inventory> toEntityList(List<InventoryDTO> inventoryList){
        return inventoryList.stream().map(InventoryMapper::toEntity).toList();
    }
}
