package echo.payload;

import javax.validation.constraints.NotBlank;

// import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}