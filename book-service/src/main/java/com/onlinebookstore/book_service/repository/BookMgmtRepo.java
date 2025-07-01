package com.onlinebookstore.book_service.repository;

import com.onlinebookstore.book_service.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMgmtRepo extends JpaRepository<Book, Integer> {
}
