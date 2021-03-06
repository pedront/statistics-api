package com.n26.statistics.api.controller.exception.handler;

import com.n26.statistics.api.controller.validator.annotation.NotTooOld;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handler for all the exceptions
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Captures the MethodArgumentNotValidException exception to change the http status to
     * NO_CONTENT.
     *
     * This exception is launched when the validation for the transaction fails, in case it is older
     * than 60 seconds.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {

            if (NotTooOld.class.getSimpleName().equals(fieldError.getCode())) {
                return handleExceptionInternal(ex, null, headers, HttpStatus.NO_CONTENT, request);
            }
        }

        return handleExceptionInternal(ex, null, headers, status, request);
    }

}
