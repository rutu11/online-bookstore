package com.onlinebookstore.book_service.controller;

import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.service.BookMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Book> addBookToRepo(@RequestBody Book newBook){
        Book book = bookMgmtService.addBookToRepo(newBook);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> allBooks = bookMgmtService.getAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book = bookMgmtService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable int id, Book updatedBook){
        Book book = bookMgmtService.updateBookById(id, updatedBook);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllBooks(){
        bookMgmtService.deleteAllBooks();
        return new ResponseEntity<>("All books deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable int id){
        bookMgmtService.deleteBookById(id);
        return new ResponseEntity<>(id+": Book deleted", HttpStatus.OK);
    }
}
