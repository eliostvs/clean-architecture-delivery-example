package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.StoreData;
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
import java.util.List;

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
    static class Config {}

    @Test
    public void findByNameContainingIgnoreCaseReturnsAllMatchStores() {
        // given
        CousineData cousineData = entityManager.persistFlushFind(CousineData.withName("name"));

        // and
        Arrays.stream(new String[]{"aAbc", "abBc", "abCc"})
                .forEach(name -> {
                    final StoreData storeData = new StoreData(null, name, name, cousineData);
                    entityManager.persistAndFlush(storeData);
                });

        // when
        List<StoreData> actual = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("aAbc", "abCc");
    }
}