package dev.nau.organizeup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiException> handleEmailExists(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(new ApiException(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiException> handleInvalidLogin(InvalidCredentialsException ex) {
        return new ResponseEntity<>(new ApiException(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiException> handleRuntime(RuntimeException ex) {
        return new ResponseEntity<>(new ApiException("Error interno: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "Entrada inválida";
        return new ResponseEntity<>(new ApiException(message), HttpStatus.BAD_REQUEST);
    }

}
