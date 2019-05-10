package echo.payload;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;


@Data // @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}