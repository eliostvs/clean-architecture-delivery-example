package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class SearchCousineByNameUseCase extends UseCase<SearchCousineByNameUseCase.InputValues, SearchCousineByNameUseCase.OutputValues> {

    private CousineRepository repository;

    public SearchCousineByNameUseCase(CousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.searchByName(input.getSearchText()));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final String searchText;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Cousine> cousines;
    }
}
