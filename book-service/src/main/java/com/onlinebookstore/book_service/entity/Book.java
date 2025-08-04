package com.onlinebookstore.book_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIdentityInfo(property = "bookId",
generator = ObjectIdGenerators.PropertyGenerator.class)
@Table(name="book-details")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @NotBlank(message = "Book title is mandatory")
    private String title;

    @NotBlank(message = "Author name is mandatory")
    private String author;

    @Positive(message = "Price should be positive")
    private double price;

    @OneToOne(mappedBy = "bookDetails")
//    @JsonBackReference
    private Inventory inventory;
}
