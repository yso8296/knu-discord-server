package knu.discord.global.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends CustomException {

    private static final String DEFAULT_TITLE = "Data Not Found";

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, DEFAULT_TITLE);
    }
}
