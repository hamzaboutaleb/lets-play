package play.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import play.dto.common.ErrorResponse;

import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.of("Unauthorized: " + authException.getMessage());
        String json = String.format("""
            {"success":%s,"error":{"message":"%s"},"timestamp":"%s"}
            """,
            errorResponse.isSuccess(),
            errorResponse.getError().getMessage().replace("\"", "\\\""),
            errorResponse.getTimestamp()
        );
        response.getWriter().write(json);
    }
}