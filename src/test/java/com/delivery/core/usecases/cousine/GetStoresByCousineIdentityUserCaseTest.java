package com.delivery.core.usecases.cousine;

import com.delivery.core.TestCoreEntityGenerator;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetStoresByCousineIdentityUserCaseTest {

    @InjectMocks
    private GetStoresByCousineIdentityUserCase userCase;

    @Mock
    private CousineRepository repository;

    @Test
    public void returnsStoresWhenCousineIdIsFound() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Set<Store> stores = new HashSet<>();
        stores.add(store);

        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doReturn(stores)
                .when(repository)
                .getStoresByIdentity(id);

        // when
        final Set<Store> actual = userCase.execute(id);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void throwsExceptionWhenCousineIdIsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doReturn(new HashSet<>())
                .when(repository)
                .getStoresByIdentity(id);

        // then
        assertThatThrownBy(() -> userCase.execute(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No cousine found for identity: " + id.getNumber());
    }
}