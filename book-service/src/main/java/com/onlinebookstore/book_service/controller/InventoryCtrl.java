package com.onlinebookstore.book_service.controller;

import com.onlinebookstore.book_service.entity.Inventory;
import com.onlinebookstore.book_service.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryCtrl {

    private final InventoryService inventoryService;

    public InventoryCtrl(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Inventory> addStock(@RequestParam int bookId,
                                              @RequestParam long stock) {
        Inventory data = inventoryService.addStock(bookId, stock);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/check-stock/{bookId}")
    public ResponseEntity<Inventory> fetchStockForABook(@PathVariable int bookId) {
        Inventory inventory = inventoryService.fetchStockOfABook(bookId);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> fetchInventory() {
        List<Inventory> allStock = inventoryService.fetchInventory();
        return ResponseEntity.ok(allStock);
    }

    @PutMapping("/update-stock")
    public ResponseEntity<Inventory> updateStockForABook(@RequestParam int bookId,
                                                         @RequestParam long stock) {
        Inventory updatedData = inventoryService.updateStockForABook(bookId, stock);
        return ResponseEntity.ok(updatedData);
    }


}
