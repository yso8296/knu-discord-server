package knu.discord.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final String title;

    public CustomException(String message, HttpStatus status, String title) {
        super(message);
        this.status = status;
        this.title = title;
    }
}
