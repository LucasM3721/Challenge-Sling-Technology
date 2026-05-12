package com.sling.technology.infrastructure.adapter.in.web;

import com.sling.technology.domain.exception.DomainException;
import com.sling.technology.domain.exception.SearchNotFoundException;
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

import static com.sling.technology.domain.exception.DomainMessages.*;
import static com.sling.technology.infrastructure.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

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
        DomainException exception = new DomainException(CHECK_IN_BEFORE_CHECK_OUT);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleDomainException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(CHECK_IN_BEFORE_CHECK_OUT, response.getBody().get(ERROR_KEY))
        );
    }

    @Test
    void shouldReturnNotFoundForSearchNotFoundException() {
        SearchNotFoundException exception = new SearchNotFoundException(SEARCH_NOT_FOUND);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleSearchNotFoundException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(SEARCH_NOT_FOUND, response.getBody().get(ERROR_KEY))
        );
    }

    @Test
    void shouldReturnBadRequestForHttpMessageNotReadableException() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleHttpMessageNotReadableException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(INVALID_PAYLOAD_FORMAT, response.getBody().get(ERROR_KEY))
        );
    }

    @Test
    void shouldReturnBadRequestForIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("SearchId cannot be null or empty");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleIllegalArgumentException(exception);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("SearchId cannot be null or empty", response.getBody().get(ERROR_KEY))
        );
    }
}
