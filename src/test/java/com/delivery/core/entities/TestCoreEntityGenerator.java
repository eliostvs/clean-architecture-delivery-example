package com.delivery.core.entities;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.domain.OrderItem;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.github.javafaker.Faker;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TestCoreEntityGenerator {
    private static final Faker faker = new Faker();

    public static Integer randomQuantity() {
        return randomNumberBetweenFiveAndTen();
    }

    public static Cousine randomCousine() {
        return new Cousine(
                randomId(),
                faker.name().name()
        );
    }

    public static List<Cousine> randomCousines() {
        return randomItemsOf(TestCoreEntityGenerator::randomCousine);
    }

    public static Identity randomId() {
        return new Identity(faker.number().randomNumber());
    }

    public static Store randomStore() {
        return new Store(
                randomId(),
                faker.name().name(),
                faker.address().streetAddress(),
                randomCousine()
        );
    }

    public static Product randomProduct() {
        return new Product(
                randomId(),
                faker.name().name(),
                faker.name().fullName(),
                faker.number().randomDouble(2, 1, 50),
                randomStore()
        );
    }

    private static int randomNumberBetweenFiveAndTen() {
        return faker.number().numberBetween(5, 10);
    }

    public static Customer randomCustomer() {
        return new Customer(
                randomId(),
                faker.name().name(),
                faker.internet().emailAddress(),
                faker.address().fullAddress(),
                faker.lorem().fixedString(30)
        );
    }

    private static <T> List<T> randomItemsOf(Supplier<T> generator) {
        return IntStream.rangeClosed(0, randomNumberBetweenFiveAndTen())
                .mapToObj(number -> (T) generator.get())
                .collect(Collectors.toList());
    }

    public static Order randomOrder() {
        return Order.newInstance(
                randomId(),
                randomCustomer(),
                randomStore(),
                Collections.singletonList(randomOrderItem())
        );
    }

    public static OrderItem randomOrderItem() {
        return OrderItem.newInstance(
                randomId(),
                randomProduct(),
                randomQuantity()
        );
    }
}
