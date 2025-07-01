package com.onlinebookstore.book_service.service;

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

    public Book addBookToRepo(Book newBook){
        return bookMgmtRepo.save(newBook);
    }

    public List<Book> getAllBooks(){
        return bookMgmtRepo.findAll();
    }

    public Book getBookById(int id){
        return bookMgmtRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book with ID: "+id+" not found"));
    }

    public Book updateBookById(int id, Book updatedBook){
        Book existingBook = bookMgmtRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book with ID: "+id+" not found"));

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setName(updatedBook.getName());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setQuantity(updatedBook.getQuantity());

        return bookMgmtRepo.save(existingBook);
    }

    public void deleteBookById(int id){
        if(!bookMgmtRepo.existsById(id)){
            throw new BookNotFoundException("Book with ID: "+id+" not found");
        }
        bookMgmtRepo.deleteById(id);
    }

    public void deleteAllBooks(){
        bookMgmtRepo.deleteAll();
    }
}
