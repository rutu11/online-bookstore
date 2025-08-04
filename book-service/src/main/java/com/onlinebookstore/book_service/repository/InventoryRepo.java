package com.onlinebookstore.book_service.repository;

import com.onlinebookstore.book_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByBookDetails_BookId(int bookId);
}
