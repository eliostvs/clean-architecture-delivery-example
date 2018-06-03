package com.delivery.core.usecases.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GetProductsByStoreAndProductsIdUseCase extends UseCase<GetProductsByStoreAndProductsIdUseCase.InputValues, GetProductsByStoreAndProductsIdUseCase.OutputValues> {
    private ProductRepository repository;

    public GetProductsByStoreAndProductsIdUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        final List<Identity> distinctProductsId = distinctIds(input.getProductsId());

        List<Product> foundProducts = repository
                .searchProductsByStoreAndProductsId(input.getStoreId(), distinctProductsId);

        throwIfAnyProductIsNotFound(distinctProductsId, foundProducts);

        return new OutputValues(foundProducts);
    }

    private void throwIfAnyProductIsNotFound(List<Identity> distinctProductsId,
                                             List<Product> foundProducts) {
        if (distinctProductsId.size() != foundProducts.size()) {
            final String message = createErrorMessage(distinctProductsId, foundProducts);
            throw new NotFoundException(message);
        }
    }

    private String createErrorMessage(List<Identity> distinctProductsId, List<Product> foundProducts) {
        List<String> missingProductsId = getMissingProductsId(distinctProductsId, foundProducts);
        return String.format("Product(s) %s not found", String.join(", ", missingProductsId));
    }

    private List<String> getMissingProductsId(List<Identity> distinctProductsId, List<Product> foundProducts) {
        Set<Long> distinctProductsIdSet = createDistinctProductsIdSet(distinctProductsId);
        Set<Long> foundProductsId = createFoundProductsIdSet(foundProducts);
        distinctProductsIdSet.removeAll(foundProductsId);

        return distinctProductsIdSet
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    private Set<Long> createFoundProductsIdSet(List<Product> foundProducts) {
        return foundProducts
                .stream()
                .map(Product::getId)
                .map(Identity::getNumber)
                .collect(Collectors.toSet());
    }

    private Set<Long> createDistinctProductsIdSet(List<Identity> distinctProductsId) {
        return distinctProductsId
                .stream()
                .map(Identity::getNumber)
                .collect(Collectors.toSet());
    }

    private List<Identity> distinctIds(List<Identity> identities) {
        return identities
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity storeId;
        private final List<Identity> productsId;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Product> products;
    }
}
