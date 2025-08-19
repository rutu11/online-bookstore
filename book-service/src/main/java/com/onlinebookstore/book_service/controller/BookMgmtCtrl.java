package com.onlinebookstore.book_service.controller;

import com.onlinebookstore.book_service.dto.BookDTO;
import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.service.BookMgmtService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BookMgmtCtrl.class);

    @Autowired
    public BookMgmtCtrl(BookMgmtService service) {
        bookMgmtService = service;
    }

    @PostMapping
    public ResponseEntity<Book> addBookToRepo(@Valid @RequestBody BookDTO newBook) {
        LOGGER.info("Adding new book => "+ newBook.toString());

        Book book = bookMgmtService.addBookToRepo(newBook);
        URI location = URI.create("/books/" + book.getBookId()); //In headers
        return ResponseEntity.created(location).body(book);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        LOGGER.info("Fetching all books..");
        List<BookDTO> allBooks = bookMgmtService.getAllBooks();
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        LOGGER.info("Fetching book details by book Id: {}", id);

        BookDTO book = bookMgmtService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable int id, BookDTO updatedBook) {
        LOGGER.info("Updating book details for book Id: {}", id);

        Book book = bookMgmtService.updateBookById(id, updatedBook);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable int id) {
        LOGGER.info("Deleting book details for book Id: {}", id);

        bookMgmtService.deleteBookById(id);
        return ResponseEntity.ok("Book deleted with ID: "+id);
//        return new ResponseEntity<>(id + ": Book deleted", HttpStatus.OK);
//        return ResponseEntity.noContent().build(); //NO BODY
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllBooks() {
        LOGGER.info("Deleting all available books..");

        bookMgmtService.deleteAllBooks();
        return new ResponseEntity<>("All books deleted", HttpStatus.OK);
    }

}
