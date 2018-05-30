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
    public GetProductsByStoreIdentityUseCase getProductsByStoreIdentityUseCase(StoreRepository storeRepository) {
        return new GetProductsByStoreIdentityUseCase(storeRepository);
    }

    @Bean
    public GetStoreByIdentityUseCase getStoreByIdentityUseCase(StoreRepository storeRepository) {
        return new GetStoreByIdentityUseCase(storeRepository);
    }

    @Bean
    public SearchStoresByNameUseCase searchStoresByNameUseCase(StoreRepository storeRepository) {
        return new SearchStoresByNameUseCase(storeRepository);
    }

    @Bean
    public GetAllStoresUseCase getAllStoresUseCase(StoreRepository storeRepository) {
        return new GetAllStoresUseCase(storeRepository);
    }

    @Bean
    public GetStoresByCousineIdentityUserCase getCousineByIdUserCase(CousineRepository cousineRepository) {
        return new GetStoresByCousineIdentityUserCase(cousineRepository);
    }

    @Bean
    public GetAllCousinesUseCase getAllCousinesUseCase(CousineRepository cousineRepository) {
        return new GetAllCousinesUseCase(cousineRepository);
    }

    @Bean
    public SearchCousineByNameUseCase searchCousineByNameUseCase(CousineRepository cousineRepository) {
        return new SearchCousineByNameUseCase(cousineRepository);
    }
}
