package com.onlinebookstore.book_service.repository;

import com.onlinebookstore.book_service.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMgmtRepo extends JpaRepository<Book, Integer> {
}
