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
import static com.delivery.TestEntityGenerator.randomName;
import static com.delivery.TestEntityGenerator.randomPrice;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaProductRepositoryTest {

    @Autowired
    private JpaProductRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @AutoConfigurationPackage
    @Configuration
    @EntityScan("com.delivery.data.db.jpa.entities")
    static class Config {
    }

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void findByStoreIdAndIdIsInReturnListOfProductData() {
        // given
        CousineData cousineData = entityManager.persistFlushFind(CousineData.newInstance("name"));
        StoreData storeData = entityManager.persistFlushFind(new StoreData(null,"name", randomAddress(), cousineData, new HashSet<>()));
        ProductData productData = entityManager.persistAndFlush(new ProductData(null, "name", "description", randomPrice(), storeData));

        // when
        List<ProductData> actual =
                repository.findByStoreIdAndIdIsIn(storeData.getId(), singletonList(productData.getId()));

        // then
        assertThat(actual).extracting("id").containsOnly(productData.getId());
    }

    @Test
    public void findByNameContainingIgnoreCase() {
        // given
        CousineData cousineData = entityManager.persistFlushFind(CousineData.newInstance(randomName()));
        StoreData storeData = entityManager.persistFlushFind(new StoreData(null, randomName(), randomAddress(), cousineData, new HashSet<>()));

        Arrays.stream(new String[]{"AABC", "ABBC", "ABCC"})
                .forEach(name -> {
                    String description = name;

                    if ("ABBC".equals(name)) {
                        description = "DESCRIPTION";
                    }

                    entityManager.persistAndFlush(new ProductData(null, name, description, randomPrice(), storeData));
                });

        // when
        List<ProductData> actual = repository.findByNameContainingOrDescriptionContainingAllIgnoreCase("abc", "des");

        // then
        assertThat(actual).hasSize(3).extracting("name").containsOnly("AABC", "ABBC", "ABCC");
    }
}