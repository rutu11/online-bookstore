package com.onlinebookstore.book_service.controller;

import com.onlinebookstore.book_service.entities.Book;
import com.onlinebookstore.book_service.exceptions.BookNotFoundException;
import com.onlinebookstore.book_service.services.BookMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookMgmtCtrl {

    private final BookMgmtService bookMgmtService;

    @Autowired
    public BookMgmtCtrl(BookMgmtService service) {
        bookMgmtService = service;
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> addBookToRepo(@RequestBody Book newBook){
        Book book = bookMgmtService.addBookToRepo(newBook);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/getallbooks")
    public ResponseEntity<?> getAllBooks(){
        List<Book> allBooks = bookMgmtService.getAllBooks();
        if(allBooks.isEmpty()){
           return new ResponseEntity<>(new BookNotFoundException("Currently no books added!!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("/getbookbyid/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id){
        Book book = bookMgmtService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/deleteallbooks")
    public ResponseEntity<?> deleteAllBooks(){
        bookMgmtService.deleteAllBooks();
        return new ResponseEntity<>("All books deleted", HttpStatus.OK);
    }

//add book
    //update book
    //get books
//    get books by id
}
