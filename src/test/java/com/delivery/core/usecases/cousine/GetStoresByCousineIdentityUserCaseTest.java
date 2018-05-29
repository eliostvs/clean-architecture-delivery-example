package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
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
        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doReturn(Collections.singletonList(store))
                .when(repository)
                .getStoresByIdentity(id);

        // when
        final List<Store> actual = userCase.execute(id);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void throwsExceptionWhenCousineIdIsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doReturn(Collections.emptyList())
                .when(repository)
                .getStoresByIdentity(id);

        // then
        assertThatThrownBy(() -> userCase.execute(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No cousine found for identity: " + id.getNumber());
    }
}