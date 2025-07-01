package com.onlinebookstore.book_service.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(){
        super("Resource not found!");
    }
}
