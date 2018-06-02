package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetAllCousinesUseCase extends UseCase<GetAllCousinesUseCase.InputValues, GetAllCousinesUseCase.OutputValues> {
    private CousineRepository repository;

    public GetAllCousinesUseCase(CousineRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.getAll());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Cousine> cousines;
    }
}
