package com.delivery.presenter.usecases.security;

import com.delivery.core.usecases.UseCase;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCustomerUseCase implements UseCase<UsernamePasswordAuthenticationToken, String> {
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    public AuthenticateCustomerUseCase(AuthenticationManager authenticationManager,
                                       JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String execute(UsernamePasswordAuthenticationToken authenticationToken) {
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return jwtProvider.generateToken(authentication);
    }
}
