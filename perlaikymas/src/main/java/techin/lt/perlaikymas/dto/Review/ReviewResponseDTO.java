package techin.lt.perlaikymas.dto.Review;

public record ReviewResponseDTO(long id,
                                long userId,
                                long movieId,
                                int rating,
                                String comment) {
}
