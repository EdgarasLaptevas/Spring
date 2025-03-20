package techin.lt.perlaikymas.dto.Review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import techin.lt.perlaikymas.model.User;

public record ReviewRequestDTO(@NotNull
                               User user,

                               @Min(1)
                               @Max(10)
                               int rating,

                               String comment) {
}
