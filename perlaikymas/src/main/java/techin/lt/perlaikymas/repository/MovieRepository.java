package techin.lt.perlaikymas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techin.lt.perlaikymas.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
