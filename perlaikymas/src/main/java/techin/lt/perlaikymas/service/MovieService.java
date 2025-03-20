package techin.lt.perlaikymas.service;

import techin.lt.perlaikymas.model.Movie;
import techin.lt.perlaikymas.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    public final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findMovieById(long id) {
        return movieRepository.findById(id);
    }

    public boolean movieExistById(long id) {
        return movieRepository.existsById(id);
    }

    public void deleteMovieById(long id) {
        movieRepository.deleteById(id);
    }

}
