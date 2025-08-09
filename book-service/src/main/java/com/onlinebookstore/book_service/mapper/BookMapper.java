package com.onlinebookstore.book_service.mapper;

import com.onlinebookstore.book_service.dto.BookDTO;
import com.onlinebookstore.book_service.entity.Book;

import java.util.List;

public class BookMapper {
    //DTO to Entity
    public static Book toEntity(BookDTO dto){
        if(dto == null) return null;

        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .price(dto.getPrice())
                .build();
    }

    public static BookDTO toDTO(Book entity){
        if(entity == null) return null;

        BookDTO bookDto = new BookDTO();
        bookDto.setBookId(entity.getBookId());
        bookDto.setTitle(entity.getTitle());
        bookDto.setAuthor(entity.getAuthor());
        bookDto.setPrice(entity.getPrice());

        return bookDto;
    }

    public static List<BookDTO> toDTOList(List<Book> booksList){
        return booksList.stream().map(BookMapper::toDTO).toList();
    }

    public static List<Book> toEntityList(List<BookDTO> booksList){
        return booksList.stream().map(BookMapper::toEntity).toList();
    }
}
