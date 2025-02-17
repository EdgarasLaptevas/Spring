package lt.techin.books.service;

import lt.techin.books.model.Book;
import lt.techin.books.repository.BookRepository;
import lt.techin.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookByID(long id) {
        bookRepository.deleteById(id);
    }

    public boolean existById(long id) {
        return bookRepository.existsById(id);
    }

    public Page<Book> findAllBooksPage(int page, int size, String sort) {

        if (sort == null) {
            Pageable pageable = PageRequest.of(page, size);
            return bookRepository.findAll(pageable);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        return bookRepository.findAll(pageable);

    }
    
}
