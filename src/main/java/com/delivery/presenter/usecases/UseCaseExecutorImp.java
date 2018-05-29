package com.delivery.presenter.usecases;

import com.delivery.core.usecases.UseCase;
import com.delivery.core.usecases.UseCaseExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class UseCaseExecutorImp implements UseCaseExecutor {
    @Override
    public <RQ, RX, I, O> CompletableFuture<RX> execute(
            UseCase<I, O> useCase, RQ request, Function<RQ, I> inputMapper, Function<O, RX> outputMapper) {
        return CompletableFuture
                .supplyAsync(() -> inputMapper.apply(request))
                .thenApplyAsync(useCase::execute)
                .thenApplyAsync(outputMapper);
    }
}
