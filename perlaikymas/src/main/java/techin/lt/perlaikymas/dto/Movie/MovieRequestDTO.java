package techin.lt.perlaikymas.dto.Movie;

import jakarta.validation.constraints.*;

public record MovieRequestDTO(@NotNull
                              @Size(min = 2, max = 255, message = "Movie title must be between 2 and 255 characters")
                              String title,

                              @NotNull
                              @Min(value = 1900, message = "Release year must be from 1900 onward")
                              int releaseYear,

                              @Pattern(regexp = "^[A-Za-z ]+$", message = "Only letters and spaces allowed")
                              String genre
) {
}
