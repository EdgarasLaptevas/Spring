package lt.techin.books.dto;

import lt.techin.books.model.BookDetails;
import lt.techin.books.model.Category;
import lt.techin.books.model.Review;

import java.util.List;

public record BookTDOReturn(String title,
                            String author, List<Review> reviews,
                            List<Category> categories, BookDetails bookDetails) {
}
