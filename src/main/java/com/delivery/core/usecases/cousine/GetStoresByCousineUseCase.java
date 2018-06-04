package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetStoresByCousineUseCase extends UseCase<GetStoresByCousineUseCase.InputValues, GetStoresByCousineUseCase.OutputValues> {
    private CousineRepository repository;

    public GetStoresByCousineUseCase(CousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity id = input.getId();

        List<Store> stores = repository.getStoresById(id);

        if (stores.isEmpty()) {
            throw new NotFoundException("Cousine %s not found", id.getNumber());
        }

        return new OutputValues(stores);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Store> stores;
    }
}
