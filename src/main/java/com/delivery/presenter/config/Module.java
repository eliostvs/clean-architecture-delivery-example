package com.delivery.presenter.config;

import com.delivery.core.usecases.cousine.CousineRepository;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetStoresByCousineIdentityUserCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.core.usecases.product.GetProductByIdentityUseCase;
import com.delivery.core.usecases.product.ProductRepository;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.GetProductsByStoreIdentityUseCase;
import com.delivery.core.usecases.store.GetStoreByIdentityUseCase;
import com.delivery.core.usecases.store.SearchStoresByNameUseCase;
import com.delivery.core.usecases.store.StoreRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {

    @Bean
    public GetProductByIdentityUseCase getProductByIdentityUseCase(ProductRepository repository) {
        return new GetProductByIdentityUseCase(repository);
    }

    @Bean
    public GetAllProductsUseCase getAllProductsUseCase(ProductRepository repository) {
        return new GetAllProductsUseCase(repository);
    }

    @Bean
    public GetProductsByStoreIdentityUseCase getProductsByStoreIdentityUseCase(StoreRepository repository) {
        return new GetProductsByStoreIdentityUseCase(repository);
    }

    @Bean
    public GetStoreByIdentityUseCase getStoreByIdentityUseCase(StoreRepository repository) {
        return new GetStoreByIdentityUseCase(repository);
    }

    @Bean
    public SearchStoresByNameUseCase searchStoresByNameUseCase(StoreRepository repository) {
        return new SearchStoresByNameUseCase(repository);
    }

    @Bean
    public GetAllStoresUseCase getAllStoresUseCase(StoreRepository repository) {
        return new GetAllStoresUseCase(repository);
    }

    @Bean
    public GetStoresByCousineIdentityUserCase getCousineByIdUserCase(CousineRepository repository) {
        return new GetStoresByCousineIdentityUserCase(repository);
    }

    @Bean
    public GetAllCousinesUseCase getAllCousinesUseCase(CousineRepository repository) {
        return new GetAllCousinesUseCase(repository);
    }

    @Bean
    public SearchCousineByNameUseCase searchCousineByNameUseCase(CousineRepository repository) {
        return new SearchCousineByNameUseCase(repository);
    }
}
