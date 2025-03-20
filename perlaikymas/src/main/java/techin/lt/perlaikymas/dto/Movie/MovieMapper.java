package techin.lt.perlaikymas.dto.Movie;

import techin.lt.perlaikymas.model.Movie;

import java.util.List;

public class MovieMapper {

    public static Movie toMovie(MovieRequestDTO movieRequestDTO) {

        return new Movie(movieRequestDTO.genre(),
                movieRequestDTO.releaseYear(), movieRequestDTO.title(), null);
    }

    public static MovieResponseDTO toMovieResponseDTO(Movie movie) {
        return new MovieResponseDTO(movie.getId(), movie.getTitle(), movie.getReleaseYear(), movie.getGenre());
    }

    public static List<MovieResponseDTO> toMovieResponseListDTO(List<Movie> movies) {
        return movies.stream()
                .map(MovieMapper::toMovieResponseDTO)
                .toList();
    }
}


