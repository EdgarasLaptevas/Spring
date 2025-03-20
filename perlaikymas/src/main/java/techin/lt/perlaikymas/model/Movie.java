package techin.lt.perlaikymas.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String genre;
    private int releaseYear;
    private String title;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    List<Review> reviews;

    public Movie(String genre, int releaseYear, String title, List<Review> reviews) {
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.title = title;
        this.reviews = reviews;
    }

    public Movie() {
    }

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}