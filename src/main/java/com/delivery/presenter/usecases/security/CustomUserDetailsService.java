package com.delivery.presenter.usecases.security;

import com.delivery.core.usecases.customer.CustomerRepository;
import com.delivery.data.db.jpa.entities.CustomerData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerData customerData = customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", email)));

        return UserPrincipal.from(customerData);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        CustomerData customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", id)));

        return UserPrincipal.from(customer);
    }
}
