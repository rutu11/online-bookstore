package com.onlinebookstore.book_service.services;

import com.onlinebookstore.book_service.entities.Book;
import com.onlinebookstore.book_service.exceptions.BookNotFoundException;
import com.onlinebookstore.book_service.repository.BookMgmtRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookMgmtService {

    private BookMgmtRepo bookMgmtRepo;

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
        return bookMgmtRepo.findById(id).orElseThrow(BookNotFoundException::new);
    }
    
    public void deleteAllBooks(){
        bookMgmtRepo.deleteAll();
    }
}
