package play.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private boolean success = false;
    private ApiError error;
    private String timestamp = Instant.now().toString();

    public static ErrorResponse of(String message) {
        return new ErrorResponse(false, new ApiError(message, null), Instant.now().toString());
    }

    public static ErrorResponse of(String message, Map<String, Object> details) {
        return new ErrorResponse(false, new ApiError(message, details), Instant.now().toString());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiError {
        private String message;
        private Map<String, Object> details;
    }
}