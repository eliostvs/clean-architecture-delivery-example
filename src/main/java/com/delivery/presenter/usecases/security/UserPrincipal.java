package com.delivery.presenter.usecases.security;

import com.delivery.data.db.jpa.entities.CustomerData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "email", "password", "address"})
public class UserPrincipal implements UserDetails {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String address;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal from(CustomerData customer) {
        return new UserPrincipal(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getAddress(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
