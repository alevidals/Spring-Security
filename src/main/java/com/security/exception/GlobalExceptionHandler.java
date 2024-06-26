package com.security.exception;

import com.security.dto.ErrorDto;
import com.security.utils.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequiredHeaderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleRequiredHeaderNotFoundException(RequiredHeaderNotFoundException ex) {
        ErrorDto errorDto = ErrorUtils.getErrorDto(ex.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<ErrorDto> handleTokenNotValidException(TokenNotValidException ex) {
        ErrorDto errorDto = ErrorUtils.getErrorDto(ex.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorDto errorDto = ErrorUtils.getErrorDto(ex.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }
}
