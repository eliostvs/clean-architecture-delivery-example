package com.delivery.presenter.usecases.security;

import com.delivery.core.usecases.UseCase;
import lombok.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCustomerUseCase extends UseCase<AuthenticateCustomerUseCase.InputValues, AuthenticateCustomerUseCase.OutputValues> {
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    public AuthenticateCustomerUseCase(AuthenticationManager authenticationManager,
                                       JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Authentication authentication = authenticationManager.authenticate(input.getAuthenticationToken());

        return new OutputValues(jwtProvider.generateToken(authentication));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final UsernamePasswordAuthenticationToken authenticationToken;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final String jwtToken;
    }
}
