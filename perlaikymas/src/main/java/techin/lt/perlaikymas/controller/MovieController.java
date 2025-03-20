package techin.lt.perlaikymas.controller;

import jakarta.validation.Valid;
import techin.lt.perlaikymas.dto.Movie.MovieMapper;
import techin.lt.perlaikymas.dto.Movie.MovieResponseDTO;
import techin.lt.perlaikymas.dto.Movie.MovieRequestDTO;
import techin.lt.perlaikymas.model.Movie;
import techin.lt.perlaikymas.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/api")
public class MovieController {

    public final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieResponseDTO> addRunningEvent(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {

        Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(movieRequestDTO));

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedMovie.getId())
                        .toUri())
                .body(MovieMapper.toMovieResponseDTO(savedMovie));

    }

    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<?> deleteEvent(@PathVariable long movieId) {

        if (!movieService.movieExistById(movieId)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This movie does not exist");
        }

        movieService.deleteMovieById(movieId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {

        List<Movie> allMovies = movieService.findAllMovies();

        return ResponseEntity.ok(MovieMapper.toMovieResponseListDTO(allMovies));
    }

}
