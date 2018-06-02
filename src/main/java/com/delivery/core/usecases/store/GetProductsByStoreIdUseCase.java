package com.delivery.core.usecases.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetProductsByStoreIdUseCase extends UseCase<GetProductsByStoreIdUseCase.InputValues, GetProductsByStoreIdUseCase.OutputValues> {
    private StoreRepository repository;

    public GetProductsByStoreIdUseCase(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues inputValues) {
        Identity id = inputValues.getId();

        List<Product> products = repository.getProductsById(id);

        if (products.isEmpty()) {
            throw new NotFoundException("No store found by identity: " + id.getNumber());
        }

        return new OutputValues(products);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Product> products;
    }
}
