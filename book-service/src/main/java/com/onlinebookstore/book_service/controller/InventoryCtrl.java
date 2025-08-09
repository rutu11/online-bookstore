package com.onlinebookstore.book_service.controller;

import com.onlinebookstore.book_service.dto.InventoryDTO;
import com.onlinebookstore.book_service.entity.Inventory;
import com.onlinebookstore.book_service.service.InventoryService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Inventory> addStock(@Valid @RequestParam int bookId,
                                              @Valid @RequestParam long stock) {
        Inventory data = inventoryService.addStock(bookId, stock);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/check-stock/{bookId}")
    public ResponseEntity<InventoryDTO> fetchStockForABook(@PathVariable int bookId) {
        InventoryDTO inventory = inventoryService.fetchStockOfABook(bookId);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> fetchInventory() {
        List<InventoryDTO> allStock = inventoryService.fetchInventory();
        return ResponseEntity.ok(allStock);
    }

    @PutMapping("/update-stock")
    public ResponseEntity<Inventory> updateStockForABook(@RequestParam int bookId,
                                                         @RequestParam long stock) {
        Inventory updatedData = inventoryService.updateStockForABook(bookId, stock);
        return ResponseEntity.ok(updatedData);
    }
}
