package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class GetAllCousinesUseCase implements UseCase<Void, List<Cousine>> {
    private CousineRepository repository;

    public GetAllCousinesUseCase(CousineRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public List<Cousine> execute(Void input) {
        return repository.getAll();
    }
}
