package com.onlinebookstore.book_service.service;

import com.onlinebookstore.base_domain.events.OrderEvents;
import com.onlinebookstore.book_service.dto.InventoryDTO;
import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.entity.Inventory;
import com.onlinebookstore.book_service.exceptions.BookNotFoundException;
import com.onlinebookstore.book_service.mapper.InventoryMapper;
import com.onlinebookstore.book_service.repository.BookMgmtRepo;
import com.onlinebookstore.book_service.repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepo inventoryRepo;
    private final BookMgmtRepo bookMgmtRepo;

    @Autowired
    private KafkaTemplate<String, OrderEvents> kafkaTemplate;

    public InventoryService(InventoryRepo inventoryRepo, BookMgmtRepo bookMgmtRepo) {
        this.inventoryRepo = inventoryRepo;
        this.bookMgmtRepo = bookMgmtRepo;
    }

    public Inventory addStock(int bookId, long stock){

        Book bookExists = bookMgmtRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Unable to add Stock.. Book with ID: "+bookId+" not found"));

        Inventory stockToAdd = Inventory.builder()
                .bookDetails(bookExists)
                .stock(stock)
                .build();

        return inventoryRepo.save(stockToAdd);
    }

    public InventoryDTO fetchStockOfABook(int bookId){
        Inventory inventory = inventoryRepo.findByBookDetails_BookId(bookId).orElseThrow(() -> new RuntimeException("Inventory not found.."));
        return InventoryMapper.toDTO(inventory);
//        Optional<Inventory> bookDetailsBookId = inventoryRepo.findByBookDetails_BookId(bookId);
//        return bookDetailsBookId.orElseThrow(() -> new RuntimeException("Inventory not found.."));
    }

    public List<InventoryDTO> fetchInventory(){
        List<Inventory> allStock = inventoryRepo.findAll();
        return InventoryMapper.toDTOList(allStock);
    }

    public Inventory updateStockForABook(int bookId, long newStock){
        Inventory dataFound = inventoryRepo.findByBookDetails_BookId(bookId).orElseThrow(() -> new RuntimeException("Inventory not found.."));
        dataFound.setStock(newStock);
        return inventoryRepo.save(dataFound);
    }

    @KafkaListener(topics ="order-events", groupId = "inventory-group")
    public void handleOrderEvent(OrderEvents orderEvent){
        Inventory bookStock = inventoryRepo.findByBookDetails_BookId(orderEvent.getBookId()).orElse(null);

        if(bookStock != null && bookStock.getStock() >= orderEvent.getQuantity()){
            long updatedStock = bookStock.getStock() - orderEvent.getQuantity();
            bookStock.setStock(updatedStock);
            inventoryRepo.save(bookStock);
            orderEvent.setEventType("INVENTORY-CONFIRMED");
        }
        else {
            orderEvent.setEventType("INVENTORY-FAILED");
        }
        kafkaTemplate.send("inventory-response", orderEvent);
    }
}
