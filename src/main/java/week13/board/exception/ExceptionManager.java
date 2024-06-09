package week13.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import week13.board.dto.ApiResponse;
import week13.board.dto.ErrorDto;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<ErrorDto>> customExceptionHandler(CustomException e) {
        ErrorDto errorDto = new ErrorDto(e.getErrorCode().getHttpStatus(), e.getMessage());
        ApiResponse<ErrorDto> response = new ApiResponse<>(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getMessage(), errorDto);
        return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<ErrorDto>> handleBadCredentialsException(BadCredentialsException e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다");
        ApiResponse<ErrorDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "회원을 찾을 수 없습니다", errorDto);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<ErrorDto>> handleAuthenticationException(AuthenticationException e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, "Authentication failed");
        ApiResponse<ErrorDto> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Authentication failed", errorDto);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
