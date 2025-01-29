package movies22.controller;

import movies22.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MovieController {

    private List<Movie> movies = new ArrayList<>();

    @PostMapping("/movies")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        movies.add(movie);

        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{index}")
                                .buildAndExpand(movies.size() - 1)
                                .toUri())
                .body(movie);

    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/movies/{index}")
    public ResponseEntity<Movie> getMovie(@PathVariable int index) {
        if (index > movies.size() - 1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies.get(index));
    }

    @GetMapping("/movies/search")
    public ResponseEntity<List<Movie>> getMovieByTitle(@RequestParam String title) {
        List<Movie> foundMovie = movies.stream()
                .filter((movie) -> movie.getTitle().contains(title))
                .toList();

        return ResponseEntity.ok(foundMovie);
    }
}
