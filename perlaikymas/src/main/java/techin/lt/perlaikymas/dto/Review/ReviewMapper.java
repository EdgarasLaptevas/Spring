package techin.lt.perlaikymas.dto.Review;

import techin.lt.perlaikymas.model.Review;
import techin.lt.perlaikymas.model.Movie;

import java.util.List;

public class ReviewMapper {

    public static Review toReview(ReviewRequestDTO reviewRequestDTO, Movie movie) {

        return new Review(
                reviewRequestDTO.comment(), reviewRequestDTO.rating(), reviewRequestDTO.user(), movie
        );
    }

    public static ReviewResponseDTO toReviewResponseDTO(Review review) {

        return new ReviewResponseDTO(review.getId(), review.getUser().getId(),
                review.getMovie().getId(), review.getRating(), review.getContent());
    }

    public static List<ReviewResponseDTO> reviewResponseListDTO(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewMapper::toReviewResponseDTO)
                .toList();
    }
}
