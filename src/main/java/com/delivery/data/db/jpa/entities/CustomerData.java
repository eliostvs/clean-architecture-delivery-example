package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.customer.CreateCustomerInput;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Entity(name = "customer")
@EqualsAndHashCode(of = {"name", "email", "address", "password"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "customer")
@ToString(of = {"name", "email", "address"})
public class CustomerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String address;

    @NotEmpty
    private String password;

    public Customer fromThis() {
        return new Customer(
                new Identity(id),
                name,
                email,
                address,
                password);
    }

    public static CustomerData from(CreateCustomerInput customerInput) {
        return new CustomerData(
                null,
                customerInput.getName(),
                customerInput.getEmail(),
                customerInput.getAddress(),
                customerInput.getPassword());
    }
}
