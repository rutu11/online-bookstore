package com.onlinebookstore.order_service.services;

import com.onlinebookstore.base_domain.book.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url= "http://localhost:8080/api")
public interface BookServiceClient {

    @GetMapping("/books/{bookId}")
    ResponseEntity<BookDTO> fetchBookDetailsById(@PathVariable int bookId);
}
