package lt.techin.books.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String comment;

    public Review(long id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Review() {

    }

    public long getId() {
        return id;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
