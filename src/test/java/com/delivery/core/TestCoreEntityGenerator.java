package com.delivery.core;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
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

    private static int randomNumberBetweenFiveAndTen() {
        return faker.number().numberBetween(5, 10);
    }

    public static Identity randomIdentity() {
        return new Identity(faker.number().randomNumber());
    }
}
