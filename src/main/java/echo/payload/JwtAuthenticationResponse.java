package echo.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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