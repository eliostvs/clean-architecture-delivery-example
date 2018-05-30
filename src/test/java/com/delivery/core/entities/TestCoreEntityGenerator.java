package com.delivery.core.entities;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.customer.Customer;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCoreEntityGenerator {
    private static final Faker faker = new Faker();

    public static Cousine randomCousine() {
        return new Cousine(randomIdentity(), faker.name().name());
    }

    public static List<Cousine> randomCousines() {
        return IntStream.rangeClosed(0, randomNumberBetweenFiveAndTen())
                .mapToObj(number -> randomCousine())
                .map(object -> (Cousine) object)
                .collect(Collectors.toList());
    }

    public static Identity randomIdentity() {
        return new Identity(faker.number().randomNumber());
    }

    public static Store randomStore() {
        return new Store(
                randomIdentity(),
                faker.name().name(),
                faker.address().streetAddress(),
                randomCousine());
    }

    public static Product randomProduct() {
        return new Product(
                randomIdentity(),
                faker.name().name(),
                faker.name().fullName(),
                faker.number().randomDouble(2, 1, 50),
                randomStore());
    }

    private static int randomNumberBetweenFiveAndTen() {
        return faker.number().numberBetween(5, 10);
    }

    public static Customer randomCustomer() {
        return new Customer(
                randomIdentity(),
                faker.name().name(),
                faker.internet().emailAddress(),
                faker.address().fullAddress(),
                faker.lorem().fixedString(30));
    }
}
