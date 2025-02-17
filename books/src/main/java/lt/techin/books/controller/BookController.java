package lt.techin.books.controller;

import jakarta.validation.Valid;
import lt.techin.books.dto.BookDTO;
import lt.techin.books.dto.BookMapper;
import lt.techin.books.dto.BookMapperReturn;
import lt.techin.books.dto.BookTDOReturn;
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
    public ResponseEntity<List<BookDTO>> findBooks() {
        return ResponseEntity.ok(BookMapper.toBookDTOList(bookService.findAllBooks()));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> findBook(@PathVariable long id) {

        Optional<Book> foundBook = bookService.findBookById(id);

        if (foundBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(BookMapper.toBookDTO(foundBook.get()));
    }

    @PostMapping("/books")
    public ResponseEntity<BookTDOReturn> postBook(@Valid @RequestBody BookDTO bookDTO) {

        Book postedBook = bookService.saveBook(BookMapper.toBook(bookDTO));

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(postedBook.getId())
                        .toUri())
                .body(BookMapperReturn.toBookTDOReturn(postedBook));
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
    public ResponseEntity<?> putBook(@PathVariable long id, @Valid @RequestBody BookDTO bookDTO) {
        if (bookDTO.title().isEmpty() || bookDTO.author().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("book author or book title not found");
        }
        if (bookService.existById(id)) {
            Book bookFromDB = bookService.findBookById(id).get();

            BookMapper.updateBookFromBookTDO(bookFromDB, bookDTO);
            

            return ResponseEntity.ok(BookMapper.toBookDTO(bookService.saveBook(bookFromDB)));
        }

        Book savedBook = bookService.saveBook(BookMapper.toBook(bookDTO));

        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .replacePath("/api/books/{id}")
                                .buildAndExpand(savedBook.getId())
                                .toUri())
                .body(BookMapper.toBookDTO(savedBook));
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<?> patchBook(@PathVariable long id, @RequestBody BookDTO bookDTO) {

        Book bookFromDB = bookService.findBookById(id).get();

        bookFromDB.setReserved(bookDTO.reserved());
        bookService.saveBook(bookFromDB);

        return ResponseEntity.ok(bookDTO);


    }

    //Pagination
    @GetMapping("/books/pagination")
    public ResponseEntity<Page<BookDTO>> getBooksPage(@RequestParam int page,
                                                      @RequestParam int size,
                                                      @RequestParam(required = false) String sort) {


        return ResponseEntity.ok(BookMapper.convertToDTOPage(bookService.findAllBooksPage(page, size, sort)));

    }


}
