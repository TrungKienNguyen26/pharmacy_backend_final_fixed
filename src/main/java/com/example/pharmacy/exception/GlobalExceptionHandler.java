package com.example.pharmacy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) { return build(HttpStatus.NOT_FOUND, ex.getMessage()); }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex) { return build(HttpStatus.BAD_REQUEST, ex.getMessage()); }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedException ex) { return build(HttpStatus.UNAUTHORIZED, ex.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream().findFirst().map(e -> {
            if (e instanceof FieldError fe) return fe.getField() + ": " + fe.getDefaultMessage();
            return e.getDefaultMessage();
        }).orElse("Du lieu khong hop le");
        return build(HttpStatus.BAD_REQUEST, message);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) { return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()); }
    private ResponseEntity<?> build(HttpStatus status, String message) {
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
