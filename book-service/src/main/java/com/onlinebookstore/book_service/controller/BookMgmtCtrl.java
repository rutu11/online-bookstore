package com.onlinebookstore.book_service.controller;

import com.onlinebookstore.book_service.dto.BookDTO;
import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.entity.Inventory;
import com.onlinebookstore.book_service.service.BookMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookMgmtCtrl {

    private final BookMgmtService bookMgmtService;

    @Autowired
    public BookMgmtCtrl(BookMgmtService service) {
        bookMgmtService = service;
    }

    @PostMapping
    public ResponseEntity<Book> addBookToRepo(@RequestBody BookDTO newBook) {
        Book book = bookMgmtService.addBookToRepo(newBook);
        URI location = URI.create("/books/" + book.getBookId()); //In headers
        return ResponseEntity.created(location).body(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> allBooks = bookMgmtService.getAllBooks();
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookMgmtService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable int id, BookDTO updatedBook) {
        Book book = bookMgmtService.updateBookById(id, updatedBook);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable int id) {
        bookMgmtService.deleteBookById(id);
        return ResponseEntity.ok("Book deleted with ID: "+id);
//        return new ResponseEntity<>(id + ": Book deleted", HttpStatus.OK);
//        return ResponseEntity.noContent().build(); //NO BODY
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllBooks() {
        bookMgmtService.deleteAllBooks();
        return new ResponseEntity<>("All books deleted", HttpStatus.OK);
    }

}
