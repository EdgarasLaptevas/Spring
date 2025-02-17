package lt.techin.books.dto;

import lt.techin.books.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;


public class BookMapper {

    public static List<BookDTO> toBookDTOList(List<Book> books) {
        List<BookDTO> result = books.stream()
                .map((book) -> new BookDTO(book.getId(), book.getTitle(), book.isReserved(),
                        book.getCover(), book.getPrice(), book.getCategory(), book.getAuthor(),
                        book.getReviews(), book.getCategories(),
                        book.getBookDetails()))
                .toList();
        return result;

    }

    public static BookDTO toBookDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.isReserved(),
                book.getCover(), book.getPrice(), book.getCategory(), book.getAuthor(),
                book.getReviews(), book.getCategories(),
                book.getBookDetails());
    }

    public static Book toBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());
        book.setCategory(bookDTO.category());
        book.setCover(bookDTO.cover());
        book.setPrice(bookDTO.price());
        book.setReserved(bookDTO.reserved());
        book.setReviews(bookDTO.reviews());
        book.setCategories(bookDTO.categories());
        book.setBookDetails(bookDTO.bookDetails());

        return book;
    }

    public static void updateBookFromBookTDO(Book book, BookDTO bookDTO) {

        book.setTitle(bookDTO.title());
        book.setAuthor(bookDTO.author());
        book.setCategory(bookDTO.category());
        book.setCover(bookDTO.cover());
        book.setPrice(bookDTO.price());
        book.setReserved(bookDTO.reserved());
        book.setReviews(bookDTO.reviews());
        book.setCategories(bookDTO.categories());
        book.setBookDetails(bookDTO.bookDetails());
    }

    public static Page<BookDTO> convertToDTOPage(Page<Book> booksPage) {
        List<BookDTO> bookDTOs = booksPage.getContent().stream()
                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.isReserved(),
                        book.getCover(), book.getPrice(), book.getCategory(), book.getAuthor(),
                        book.getReviews(), book.getCategories(),
                        book.getBookDetails()))
                .collect(Collectors.toList());

        return new PageImpl<>(bookDTOs, booksPage.getPageable(), booksPage.getTotalElements());
    }
}
