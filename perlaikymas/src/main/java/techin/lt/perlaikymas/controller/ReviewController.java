package techin.lt.perlaikymas.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import techin.lt.perlaikymas.dto.Review.ReviewMapper;
import techin.lt.perlaikymas.dto.Review.ReviewRequestDTO;
import techin.lt.perlaikymas.model.Review;
import techin.lt.perlaikymas.model.Movie;
import techin.lt.perlaikymas.model.User;
import techin.lt.perlaikymas.service.ReviewService;
import techin.lt.perlaikymas.service.MovieService;
import techin.lt.perlaikymas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final MovieService movieService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService registrationService, MovieService eventService, UserService userService) {
        this.reviewService = registrationService;
        this.movieService = eventService;
        this.userService = userService;
    }

    @PostMapping("/movies/{movieId}/reviews")
    public ResponseEntity<?> postReview(@PathVariable long movieId, @Valid @RequestBody ReviewRequestDTO reviewRequestDTO, Authentication authentication) {

        Optional<Movie> movieOpt = movieService.findMovieById(movieId);

        if (movieOpt.isEmpty()) {

            Map<String, String> result = new HashMap<>();
            result.put("Movie", "does not exist");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        Movie movie = movieOpt.get();

        Optional<User> userOpt = userService.findUserById(reviewRequestDTO.user().getId());

        if (userOpt.isEmpty()) {

            Map<String, String> result = new HashMap<>();
            result.put("User", "does not exist");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        User userFound = userOpt.get();

        Optional<User> thisUserOpt = userService.findUserByUsername(authentication.getName());
        if (thisUserOpt.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You are not authenticated");
        }

        User thisUser = thisUserOpt.get();


        if (thisUser.getId() != reviewRequestDTO.user().getId()) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot post review for another person");
        }

        boolean alreadyPosted = userFound.getReviews().stream()
                .anyMatch(review -> review.getMovie().getId() == movieId);

        if (alreadyPosted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot post review for the same movie again");
        }

        Review savedReview = reviewService.saveReview(ReviewMapper.toReview(reviewRequestDTO, movie));

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedReview.getId())
                        .toUri())
                .body(ReviewMapper.toReviewResponseDTO(savedReview));
    }

    @GetMapping("/movies/{movieId}/reviews")
    public ResponseEntity<?> getAllEventsParticipants(@PathVariable long movieId) {

        Optional<Movie> movieOpt = movieService.findMovieById(movieId);

        if (movieOpt.isEmpty()) {

            Map<String, String> result = new HashMap<>();
            result.put("Movie", "does not exist");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        Movie movie = movieOpt.get();

        List<Review> movieReviews = movie.getReviews();

        return ResponseEntity.ok(ReviewMapper.reviewResponseListDTO(movieReviews));
    }

}
