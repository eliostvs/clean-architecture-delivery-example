package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.ProductData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.delivery.TestEntityGenerator.randomAddress;
import static com.delivery.TestEntityGenerator.randomPrice;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaStoreRepositoryTest {

    @Autowired
    private JpaStoreRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @AutoConfigurationPackage
    @EntityScan("com.delivery.data.db.jpa.entities")
    static class Config {
    }

    private CousineData cousineData;

    @Before
    public void setUp() throws Exception {
        cousineData = entityManager.persistFlushFind(CousineData.newInstance("name"));
    }

    @Test
    public void findByNameOrDescriptionContainingIgnoreCaseAllReturnsAllMatchStores() {
        // given
        Arrays.stream(new String[]{"aAbc", "abBc", "abCc"})
                .forEach(name -> {
                    final StoreData storeData = new StoreData(null, name, randomAddress(), cousineData, new HashSet<>());
                    entityManager.persistAndFlush(storeData);
                });

        // when
        List<StoreData> actual = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("aAbc", "abCc");
    }

    @Test
    public void findProductsByIdReturnsAllProducts() {
        // given
        StoreData storeData = entityManager.persistFlushFind(new StoreData(null, "name", randomAddress(), cousineData, new HashSet<>()));

        // and
        Arrays.stream(new String[]{"product A", "product B"})
                .forEach(name -> {
                    final ProductData productData = new ProductData(null, name, "desc", randomPrice(), storeData);
                    entityManager.persistAndFlush(productData);
                });

        // when
        List<ProductData> actual = repository.findProductsById(storeData.getId());

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("product A", "product B");
    }
}