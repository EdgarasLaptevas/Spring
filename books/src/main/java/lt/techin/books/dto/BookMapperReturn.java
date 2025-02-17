package lt.techin.books.dto;

import lt.techin.books.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapperReturn {

    public static List<BookTDOReturn> toBooksTDOReturn(List<Book> books) {
        List<BookTDOReturn> booksReturn = books.stream()
                .map((book -> new BookTDOReturn(book.getTitle(), book.getAuthor(), book.getReviews(),
                        book.getCategories(), book.getBookDetails())))
                .toList();

        return booksReturn;
    }

    public static BookTDOReturn toBookTDOReturn(Book book) {
        BookTDOReturn bookTDOReturn = new BookTDOReturn(book.getTitle(), book.getAuthor(), book.getReviews(),
                book.getCategories(), book.getBookDetails());
        return bookTDOReturn;
    }
}
