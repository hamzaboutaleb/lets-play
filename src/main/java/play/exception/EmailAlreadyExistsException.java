package play.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {

    private final String code;
    private final Map<String, Object> details;

    public EmailAlreadyExistsException(String email) {
        super("Email already registered: " + email);
        this.details = Map.of("email", email);
    }
}