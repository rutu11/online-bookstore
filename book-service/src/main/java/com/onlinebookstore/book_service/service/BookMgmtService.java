package com.onlinebookstore.book_service.service;

import com.onlinebookstore.book_service.dto.BookDTO;
import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.exceptions.BookNotFoundException;
import com.onlinebookstore.book_service.repository.BookMgmtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookMgmtService {

    private final BookMgmtRepo bookMgmtRepo;

    @Autowired
    public BookMgmtService(BookMgmtRepo repo) {
        bookMgmtRepo = repo;
    }

    public Book addBookToRepo(BookDTO newBook){
        Book book = Book.builder()
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .price(newBook.getPrice())
                .build();

        return bookMgmtRepo.save(book);
    }

    public List<Book> getAllBooks(){
        return bookMgmtRepo.findAll();
    }

    public Book getBookById(int id){
        return bookMgmtRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book with ID: "+id+" not found"));
    }

    public Book updateBookById(int id, BookDTO updatedBook){
        Book book = bookMgmtRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book with ID: "+id+" not found"));

        book.setAuthor(updatedBook.getAuthor());
        book.setTitle(updatedBook.getTitle());
        book.setPrice(updatedBook.getPrice());

        return bookMgmtRepo.save(book);
    }

    public void deleteBookById(int id){
        if(!bookMgmtRepo.existsById(id)){
            throw new BookNotFoundException("Cannot delete. Book with ID: "+id+" not found");
        }
        bookMgmtRepo.deleteById(id);
    }

    public void deleteAllBooks(){
        bookMgmtRepo.deleteAll();
    }
}
