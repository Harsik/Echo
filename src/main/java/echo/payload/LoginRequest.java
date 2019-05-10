package echo.payload;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;

// @Data // @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor
@Getter
@Setter
// @Builder
// @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Builder
    public LoginRequest(String email, String password){
        this.email=email;
        this.password=password;
    }

}