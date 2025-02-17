package lt.techin.books.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.books.model.BookDetails;
import lt.techin.books.model.Category;
import lt.techin.books.model.Review;

import java.math.BigDecimal;
import java.util.List;

public record BookDTO(
        long id,

        @Size(min = 2, max = 120)
        String title,
        boolean reserved,

        @NotNull
        String cover,
        BigDecimal price,
        String category,

        @Pattern(regexp = "^[A-Z][a-z]+$", message = "Must start whit UpperCase")
        String author,

        List<Review> reviews,
        List<Category> categories,
        BookDetails bookDetails) {
}




