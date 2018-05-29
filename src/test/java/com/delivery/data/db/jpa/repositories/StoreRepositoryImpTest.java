package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.data.db.jpa.entities.StoreData;
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
public class StoreRepositoryImpTest {

    @InjectMocks
    private StoreRepositoryImp storeRepository;

    @Mock
    private JpaStoreRepository jpaStoreRepository;

    @Test
    public void getAllReturnsAllStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        StoreData storeData = StoreData.fromStore(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(jpaStoreRepository)
                .findAll();

        // when
        List<Store> actual = storeRepository.getAll();

        // then
        assertThat(actual).containsOnly(store);
    }
}