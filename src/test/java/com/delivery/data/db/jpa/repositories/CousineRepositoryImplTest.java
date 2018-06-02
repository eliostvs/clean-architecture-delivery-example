package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.data.db.jpa.entities.CousineData;
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
public class CousineRepositoryImplTest {

    @InjectMocks
    private CousineRepositoryImpl cousineRepository;

    @Mock
    private JpaCousineRepository jpaCousineRepository;

    @Test
    public void getStoresByIdentityReturnsStores() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = TestCoreEntityGenerator.randomId();

        StoreData storeData = StoreData.from(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(jpaCousineRepository)
                .findStoresById(id.getNumber());

        // when
        final List<Store> actual = cousineRepository.getStoresById(id);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void getAllReturnsAllCousines() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineData cousineData = CousineData.from(cousine);

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(jpaCousineRepository)
                .findAll();
        // when
        final List<Cousine> actual = cousineRepository.getAll();

        // then
        assertThat(actual).containsOnly(cousine);
    }

    @Test
    public void searchCousineByNameReturnsAllCousines() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineData cousineData = CousineData.from(cousine);
        String search = "abc";

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(jpaCousineRepository)
                .findByNameContainingIgnoreCase(search);

        // when
        final List<Cousine> actual = cousineRepository.searchByName(search);

        // then
        assertThat(actual).isEqualTo(Collections.singletonList(cousine));
    }
}