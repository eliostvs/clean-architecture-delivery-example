package com.delivery.core.usecases.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetStoreByIdentityUseCaseTest {

    @InjectMocks
    private GetStoreByIdentityUseCase useCase;

    @Mock
    private StoreRepository repository;

    @Test
    public void getStoreByIdentityReturnsStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();

        // and
        doReturn(Optional.of(store))
                .when(repository)
                .getByIdentity(eq(store.getId()));

        // when
        Store actual = useCase.execute(store.getId());

        // then
        assertThat(actual).isEqualTo(store);
    }

    @Test
    public void getStoreByIdentityThrowsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getByIdentity(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No store found by identity: " + id.getNumber());
    }
}