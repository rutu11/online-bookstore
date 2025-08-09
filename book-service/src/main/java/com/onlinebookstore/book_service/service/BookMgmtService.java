package com.onlinebookstore.book_service.service;

import com.onlinebookstore.book_service.dto.BookDTO;
import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.exceptions.BookNotFoundException;
import com.onlinebookstore.book_service.mapper.BookMapper;
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
        Book bookEntity = BookMapper.toEntity(newBook);
        return bookMgmtRepo.save(bookEntity);
    }

    public List<BookDTO> getAllBooks(){
        List<Book> books = bookMgmtRepo.findAll();
        return BookMapper.toDTOList(books);
    }

    public BookDTO getBookById(int id){
        System.out.println("Fetcing book by id....");
        Book book = bookMgmtRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book with ID: " + id + " not found"));
        return BookMapper.toDTO(book);
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
