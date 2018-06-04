package com.delivery;

import com.delivery.presenter.rest.api.entities.OrderRequestItem;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.usecases.security.UserPrincipal;
import com.github.javafaker.Faker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TestEntityGenerator {

    private static final Faker faker = new Faker();

    public static Long randomId() {
        return faker.number().randomNumber();
    }

    public static UserPrincipal randomUserPrincipal() {
        return new UserPrincipal(
                randomId(),
                faker.name().username(),
                randomEmail(),
                randomPassword(),
                faker.address().fullAddress(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomPassword() {
        return faker.code().isbn10();
    }

    public static OrderRequest randomOrderRequest() {
        return new OrderRequest(
                randomId(),
                randomItemsOf(TestEntityGenerator::randomOrderItemRequest));
    }

    private static OrderRequestItem randomOrderItemRequest() {
        return new OrderRequestItem(
                randomId(),
                faker.number().numberBetween(1, 50));
    }

    private static <T> List<T> randomItemsOf(Supplier<T> generator) {
        return IntStream.rangeClosed(0, randomNumberBetweenFiveAndTen())
                .mapToObj(number -> (T) generator.get())
                .collect(Collectors.toList());
    }

    private static int randomNumberBetweenFiveAndTen() {
        return faker.number().numberBetween(5, 10);
    }

    public static Double randomPrice() {
        return faker.number().randomDouble(2, 10, 100);
    }

    public static String randomName() {
        return faker.name().name();
    }

    public static String randomDescription() {
        return faker.lorem().characters(50, 100);
    }

    public static String randomAddress() {
        return faker.address().fullAddress();
    }
}
