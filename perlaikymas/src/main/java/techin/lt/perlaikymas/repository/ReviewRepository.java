package techin.lt.perlaikymas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techin.lt.perlaikymas.model.Review;
import techin.lt.perlaikymas.model.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUser(User user);
}
