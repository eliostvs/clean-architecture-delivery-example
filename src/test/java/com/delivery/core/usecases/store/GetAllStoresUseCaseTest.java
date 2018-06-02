package com.delivery.core.usecases.store;

import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetAllStoresUseCaseTest {

    @InjectMocks
    private GetAllStoresUseCase useCase;

    @Mock
    private StoreRepository repository;

    @Test
    public void returnsAllStores() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        GetAllStoresUseCase.InputValues input = new GetAllStoresUseCase.InputValues();

        // and
        doReturn(Collections.singletonList(store))
                .when(repository)
                .getAll();

        // when
        List<Store> actual = useCase.execute(input).getStores();

        // then
        assertThat(actual).containsOnly(store);
    }
}