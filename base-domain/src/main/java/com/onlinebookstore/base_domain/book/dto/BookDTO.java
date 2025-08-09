package com.onlinebookstore.base_domain.book.dto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private int bookId;

    @NotBlank(message = "Book title is mandatory")
    @Size(max = 100, message = "Title name exceeded 100 characters.")
    private String title;

    @NotBlank(message = "Author name is mandatory")
    @Size(max = 100, message = "Title name exceeded 100 characters.")
    private String author;

    @Positive(message = "Price should be positive")
    @Digits(integer = 6, fraction = 4, message = "Price must be a valid amount (e.g., 999999.9999)")
    private double price;
}
