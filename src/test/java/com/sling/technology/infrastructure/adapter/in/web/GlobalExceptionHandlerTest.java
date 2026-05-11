package com.sling.technology.infrastructure.adapter.in.web;

import com.sling.technology.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static com.sling.technology.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp(){
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void shouldReturnBadRequestWithFieldErrorsForValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("searchRequest", "hotelId", "hotelId is required");
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("hotelId is required", response.getBody().get("hotelId"))
        );
    }

    @Test
    void shouldReturnBadRequestForDomainException() {
        DomainException exception = new DomainException(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN_BEFORE_CHECK_OUT);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleDomainException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN_BEFORE_CHECK_OUT, response.getBody().get("error"))
        );
    }

    @Test
    void shouldReturnBadRequestForHttpMessageNotReadableException() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleHttpMessageNotReadableException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(INVALID_PAYLOAD_FORMAT,
                        response.getBody().get("error"))
        );
    }

    @Test
    void shouldReturnBadRequestForIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("SearchId cannot be null or empty");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleIllegalArgumentException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("SearchId cannot be null or empty", response.getBody().get("error"))
        );
    }
}
