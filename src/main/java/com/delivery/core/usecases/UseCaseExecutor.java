package com.delivery.core.usecases;

import io.micrometer.core.lang.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface UseCaseExecutor {
    <RQ, RX, I, O> CompletableFuture<RX> execute(
            UseCase<I, O> useCase,
            @Nullable RQ request,
            Function<RQ, I> inputMapper,
            Function<O, RX> outputMapper);
}
