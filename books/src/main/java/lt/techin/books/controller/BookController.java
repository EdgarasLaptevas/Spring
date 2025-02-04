package lt.techin.books.controller;

import jakarta.validation.Valid;
import lt.techin.books.model.Book;
import lt.techin.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BookController {

    final public BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> findBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> findBook(@PathVariable long id) {

        Optional<Book> foundBook = bookService.findBookById(id);

        if (foundBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundBook.get());
    }

    @PostMapping("/books")
    public ResponseEntity<Book> postBook(@Valid @RequestBody Book book) {
        System.out.println("POST request received");
        Book postedBook = bookService.saveBook(book);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(postedBook.getId())
                        .toUri())
                .body(postedBook);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        if (!bookService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBookByID(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> putBook(@Valid @PathVariable long id, @RequestBody Book book) {
        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("book author or book title not found");
        }
        if (bookService.existById(id)) {
            Book bookFromDB = bookService.findBookById(id).get();
            bookFromDB.setAuthor(book.getAuthor());
            bookFromDB.setTitle(book.getTitle());
            bookFromDB.setCategory(book.getCategory());
            bookFromDB.setPrice(book.getPrice());
            bookFromDB.setCover(book.getCover());
            bookFromDB.setReviews(book.getReviews());
            bookFromDB.setCategory(book.getCategory());
            bookFromDB.setBookDetails(book.getBookDetails());
            return ResponseEntity.ok(bookService.saveBook(bookFromDB));
        }

        Book savedBook = bookService.saveBook(book);

        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .replacePath("/api/books/{id}")
                                .buildAndExpand(savedBook.getId())
                                .toUri())
                .body(savedBook);
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<?> patchBook(@PathVariable long id, @RequestBody Book book) {
//        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("book author or book title not found");
//        }

        Book bookFromDB = bookService.findBookById(id).get();

        bookFromDB.setReserved(book.isReserved());
        return ResponseEntity.ok(bookService.saveBook(bookFromDB));

    }

    //Pagination
    @GetMapping("/books/pagination")
    public ResponseEntity<Page<Book>> getBooksPage(@RequestParam int page,
                                                   @RequestParam int size,
                                                   @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(bookService.findAllBooksPage(page, size, sort));

    }
}
