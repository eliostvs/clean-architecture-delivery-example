package com.delivery.presenter.rest.api.customer;

import com.delivery.core.usecases.customer.CreateCustomerInput;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomerInputMapperTest {

    @InjectMocks
    private CreateCustomerInputMapper inputMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void mapReturnsCreateCustomerInput() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        CreateCustomerInput createCustomerInput = SignUpRequest.from(signUpRequest);

        // and
        doReturn("encrypt")
                .when(passwordEncoder)
                .encode(eq("password"));

        // when
        CreateCustomerInput actual = inputMapper.map(signUpRequest);

        // then
        assertThat(actual).isEqualToIgnoringGivenFields(createCustomerInput, "password");
        assertThat(actual.getPassword()).isEqualTo("encrypt");
    }
}