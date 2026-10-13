package play.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class NameAlreadyTakenException extends RuntimeException {

    private final String code;
    private final Map<String, Object> details;

    public NameAlreadyTakenException(String name) {
        super("Name already taken: " + name);
        this.details = Map.of("name", name);
    }
}