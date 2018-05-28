package com.delivery.presenter.config;

import com.delivery.core.usecases.cousine.CousineRepository;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetCousineByIdentityUserCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    
    @Bean
    public GetCousineByIdentityUserCase getCousineByIdUserCase(CousineRepository cousineRepository) {
        return new GetCousineByIdentityUserCase(cousineRepository);
    }
    
    @Bean
    public GetAllCousinesUseCase getAllCousinesUseCase(CousineRepository cousineRepository)  {
        return new GetAllCousinesUseCase(cousineRepository);
    }
}
