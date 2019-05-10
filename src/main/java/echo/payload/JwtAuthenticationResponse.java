package echo.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    @Builder
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}