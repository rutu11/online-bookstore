package com.onlinebookstore.book_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

//@ToString(exclude = "bookDetails")
//@EqualsAndHashCode(exclude = "bookDetails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="book_inventory")
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int inventoryId;
    private long stock;

    //OWNING Entity
//    @JoinColumn(name = "book_id") //FK - JPA will default to using the primary key of the referenced entity (Book.id).
//    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book bookDetails;
}
