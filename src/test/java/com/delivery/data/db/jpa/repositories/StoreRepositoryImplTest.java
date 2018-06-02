package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.data.db.jpa.entities.ProductData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class StoreRepositoryImplTest {

    @InjectMocks
    private StoreRepositoryImpl storeRepository;

    @Mock
    private JpaStoreRepository jpaStoreRepository;

    @Test
    public void getAllReturnsAllStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        StoreData storeData = StoreData.from(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(jpaStoreRepository)
                .findAll();

        // when
        List<Store> actual = storeRepository.getAll();

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void searchStoresByNameReturnsAllMatchStores() {
        // given
        String text = "abc";
        Store store = TestCoreEntityGenerator.randomStore();
        StoreData storeData = StoreData.from(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(jpaStoreRepository)
                .findByNameContainingIgnoreCase(text);

        // when
        List<Store> actual = storeRepository.searchByName(text);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void getStoreByIdentityReturnsOptionalStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        StoreData storeData = StoreData.from(store);

        // and
        doReturn(Optional.of(storeData))
                .when(jpaStoreRepository)
                .findById(store.getId().getNumber());

        // when
        Optional<Store> actual = storeRepository.getById(store.getId());

        // then
        assertThat(actual).isEqualTo(Optional.of(store));
    }

    @Test
    public void getStoreByIdentityReturnsOptionalEmpty() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();

        // and
        doReturn(Optional.empty())
                .when(jpaStoreRepository)
                .findById(id.getNumber());

        // when
        Optional<Store> actual = storeRepository.getById(id);

        // then
        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    public void getProductsByIdentityReturnsProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductData productData = ProductData.from(product);
        Identity id = product.getStore().getId();

        // and
        doReturn(Collections.singletonList(productData))
                .when(jpaStoreRepository)
                .findProductsById(id.getNumber());

        //when
        List<Product> actual = storeRepository.getProductsById(id);

        // then
        assertThat(actual).containsOnly(product);
    }
}