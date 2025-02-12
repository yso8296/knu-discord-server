package knu.discord.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> methodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, Object> errors = new HashMap<>();
        e.getAllErrors()
                .forEach(
                        field -> errors.put(((FieldError) field).getField(), field.getDefaultMessage()));

        problemDetail.setTitle("Validation Error");
        problemDetail.setProperties(errors);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    private ProblemDetail setCustomProblemDetail(CustomException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatus());
        problemDetail.setTitle(e.getTitle());
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
}
