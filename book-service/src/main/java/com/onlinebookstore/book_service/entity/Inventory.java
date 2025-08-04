package com.onlinebookstore.book_service.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(property = "inventoryId",
generator = ObjectIdGenerators.PropertyGenerator.class)
@Table(name="book-inventory")
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int inventoryId;
    private long stock;

    //OWNING Entity
//    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id") //FK - JPA will default to using the primary key of the referenced entity (Book.id).
    private Book bookDetails;
}
