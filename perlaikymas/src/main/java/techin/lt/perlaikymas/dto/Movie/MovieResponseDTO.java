package techin.lt.perlaikymas.dto.Movie;

public record MovieResponseDTO(long eventId,
                               String title,
                               int releaseYear,
                               String genre
) {
}
