package lt.techin.books.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book_details")
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String isbn;
    private int pageCount;
    private String language;

    public BookDetails(long id, String isbn, int pageCount, String language) {
        this.id = id;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.language = language;
    }

    public BookDetails() {

    }

    public long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
