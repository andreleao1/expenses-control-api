package br.com.agls.expensescontrolapi.api.handlererros;

import br.com.agls.expensescontrolapi.domain.exceptions.ConstraintViolationException;
import br.com.agls.expensescontrolapi.util.UriExtractorUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> constraintViolationExceptionHandlerError(ConstraintViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Error responseBody = Error.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(ex.getMessage())
                .path(UriExtractorUtil.execute(request.toString()))
                .build();

        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> entityNotFoundExceptionHandlerError(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error responseBody = Error.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(ex.getMessage())
                .path(UriExtractorUtil.execute(request.toString()))
                .build();

        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> illegalArgumentExceptionHandlerError(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Error responseBody = Error.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(ex.getMessage())
                .path(UriExtractorUtil.execute(request.toString()))
                .build();

        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), status, request);
    }

}
