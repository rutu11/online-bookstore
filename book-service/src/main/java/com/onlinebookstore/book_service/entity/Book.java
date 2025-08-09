package com.onlinebookstore.book_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


//@ToString(exclude = "inventory")
//@EqualsAndHashCode(exclude = "inventory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "book_details")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    private String title;
    private String author;
    private double price;

//    @OneToOne(mappedBy = "bookDetails")
//    @JsonBackReference
//    private Inventory inventory;
}
