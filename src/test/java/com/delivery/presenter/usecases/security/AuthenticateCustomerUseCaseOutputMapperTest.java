package com.delivery.presenter.usecases.security;

import com.delivery.presenter.rest.api.entities.AuthenticationResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticateCustomerUseCaseOutputMapperTest {

    @Test
    public void mapReturnsResponseOkWithJwtToken() {
        // given
        String jwtToken = "jwtToken";
        AuthenticateCustomerUseCase.OutputValues output = new AuthenticateCustomerUseCase.OutputValues(jwtToken);

        // when
        ResponseEntity<AuthenticationResponse> response = AuthenticateCustomerUseCaseOutputMapper.map(output);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getToken()).isEqualTo(jwtToken);
        assertThat(response.getBody().isSuccess()).isEqualTo(true);
    }
}