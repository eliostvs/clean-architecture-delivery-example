package com.delivery.presenter.usecases.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticateCustomerUseCaseTest {

    @InjectMocks
    private AuthenticateCustomerUseCase useCase;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    public void executeReturnsJwtToken() {
        // given
        String jwtToken = "jwtToken";
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("user", "password");
        Authentication authentication = new TestingAuthenticationToken("user", "password");
        AuthenticateCustomerUseCase.InputValues input =
                new AuthenticateCustomerUseCase.InputValues(authenticationToken);

        // and
        doReturn(authentication)
                .when(authenticationManager)
                .authenticate(eq(authenticationToken));

        // and
        doReturn(jwtToken)
                .when(jwtProvider)
                .generateToken(authentication);

        // when
        String actual = useCase.execute(input).getJwtToken();

        // then
        assertThat(actual).isEqualTo(jwtToken);
    }
}