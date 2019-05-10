package echo.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private Boolean success;
    private String message;
}