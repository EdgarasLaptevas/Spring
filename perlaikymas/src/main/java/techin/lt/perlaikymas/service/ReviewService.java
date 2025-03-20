package techin.lt.perlaikymas.service;

import techin.lt.perlaikymas.model.Review;
import techin.lt.perlaikymas.model.User;
import techin.lt.perlaikymas.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findReviewById(long id) {
        return reviewRepository.findById(id);
    }

    public boolean reviewExistById(long id) {
        return reviewRepository.existsById(id);
    }

    public void deleteReviewById(long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> findReviewListByUser(User user) {
        return reviewRepository.findAllByUser(user);
    }
}
