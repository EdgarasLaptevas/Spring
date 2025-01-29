package movies22.model;

public class Movie {
    private int id;
    private String title;
    private String director;

    public Movie(String title, int id, String director) {
        this.title = title;
        this.id = id;
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
