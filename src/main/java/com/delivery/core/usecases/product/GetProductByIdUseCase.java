package com.delivery.core.usecases.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

public class GetProductByIdUseCase extends UseCase<GetProductByIdUseCase.InputValues, GetProductByIdUseCase.OutputValues> {
    private ProductRepository repository;

    public GetProductByIdUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity id = input.getId();

        return repository
                .getById(id)
                .map(OutputValues::new)
                .orElseThrow(() -> new NotFoundException("Product " + id.getNumber() + " not found"));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final Product product;
    }
}
