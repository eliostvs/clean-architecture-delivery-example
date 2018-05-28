package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class SearchCousineByNameUseCase implements UseCase<String, List<Cousine>> {

    private CousineRepository repository;

    public SearchCousineByNameUseCase(CousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cousine> execute(String search) {
        return repository.searchByName(search);
    }
}
