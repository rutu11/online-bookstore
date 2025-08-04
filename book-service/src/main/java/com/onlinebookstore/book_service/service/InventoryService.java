package com.onlinebookstore.book_service.service;

import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.entity.Inventory;
import com.onlinebookstore.book_service.exceptions.BookNotFoundException;
import com.onlinebookstore.book_service.repository.BookMgmtRepo;
import com.onlinebookstore.book_service.repository.InventoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepo inventoryRepo;
    private final BookMgmtRepo bookMgmtRepo;

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

    public Inventory fetchStockOfABook(int bookId){
        return inventoryRepo.findByBookDetails_BookId(bookId).orElseThrow(() -> new RuntimeException("Inventory not found.."));
//        Optional<Inventory> bookDetailsBookId = inventoryRepo.findByBookDetails_BookId(bookId);
//        return bookDetailsBookId.orElseThrow(() -> new RuntimeException("Inventory not found.."));
    }

    public List<Inventory> fetchInventory(){
        return inventoryRepo.findAll();
    }

    public Inventory updateStockForABook(int bookId, long newStock){
        Inventory dataFound = inventoryRepo.findByBookDetails_BookId(bookId).orElseThrow(() -> new RuntimeException("Inventory not found.."));
        dataFound.setStock(newStock);
        return inventoryRepo.save(dataFound);
    }
}
