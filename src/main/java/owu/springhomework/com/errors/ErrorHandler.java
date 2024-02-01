package owu.springhomework.com.errors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import owu.springhomework.com.dto.ErrorDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDto> handleDatabaseException(DataIntegrityViolationException exception) {
        String message = exception.getCause().getCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(message)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        String message = "Invalid request parameters. Please fix the following errors:";
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(System.currentTimeMillis())
                .details(message)
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        for (ConstraintViolation<?> violation : constraintViolations) {
            String fieldName = "";
            if (violation.getPropertyPath() != null) {
                fieldName = violation.getPropertyPath().toString();
            }

            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        String message = "Invalid request parameters. Please fix the following errors:";
        ErrorDto errorDto = ErrorDto.builder()
                .timestamp(System.currentTimeMillis())
                .details(message)
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorDto);
    }
}
