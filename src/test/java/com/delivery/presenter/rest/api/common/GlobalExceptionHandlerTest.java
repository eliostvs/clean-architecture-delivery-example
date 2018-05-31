package com.delivery.presenter.rest.api.common;

import com.delivery.core.domain.EmailAlreadyUsedException;
import com.delivery.core.domain.NotFoundException;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void handleAuthenticationExceptionReturnsBadRequest() {
        // given
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String exceptionMessage = "Error";
        AuthenticationException exception = new AuthenticationException(exceptionMessage) {
        };

        // when
        ResponseEntity<ApiResponse> actual = exceptionHandler.handleAuthenticationException(exception);

        // then
        assertResponse(actual, exceptionMessage, httpStatus);
    }

    @Test
    public void handleEmailAlreadyUsedExceptionReturnsBadRequestWith() {
        // given
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String exceptionMessage = "Error";
        EmailAlreadyUsedException exception = new EmailAlreadyUsedException(exceptionMessage);

        // when
        ResponseEntity<ApiResponse> actual = exceptionHandler.handleEmailAlreadyUsedException(exception);

        // then
        assertResponse(actual, exceptionMessage, httpStatus);
    }

    @Test
    public void handleDomainExceptionReturnsNotFound() {
        // given
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String exceptionMessage = "Error";
        NotFoundException exception = new NotFoundException(exceptionMessage);

        // when
        ResponseEntity<ApiResponse> actual = exceptionHandler.handleDomainException(exception);

        // then
        assertResponse(actual, exceptionMessage, httpStatus);
    }

    private void assertResponse(ResponseEntity<ApiResponse> actual, String exceptionMessage, HttpStatus httpStatus) {
        assertThat(actual.getStatusCode()).isEqualTo(httpStatus);
        assertThat(actual.getBody().getMessage()).isEqualTo(exceptionMessage);
        assertThat(actual.getBody().getSuccess()).isFalse();
    }
}