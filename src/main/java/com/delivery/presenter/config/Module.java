package com.delivery.presenter.config;

import com.delivery.core.usecases.cousine.CousineRepository;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetStoresByCousineIdentityUserCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.StoreRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {

    @Bean
    public GetAllStoresUseCase getAllStoresUseCase(StoreRepository storeRepository) {
        return new GetAllStoresUseCase(storeRepository);
    }
    
    @Bean
    public GetStoresByCousineIdentityUserCase getCousineByIdUserCase(CousineRepository cousineRepository) {
        return new GetStoresByCousineIdentityUserCase(cousineRepository);
    }
    
    @Bean
    public GetAllCousinesUseCase getAllCousinesUseCase(CousineRepository cousineRepository)  {
        return new GetAllCousinesUseCase(cousineRepository);
    }

    @Bean
    public SearchCousineByNameUseCase searchCousineByNameUseCase(CousineRepository cousineRepository) {
        return new SearchCousineByNameUseCase(cousineRepository);
    }
}
