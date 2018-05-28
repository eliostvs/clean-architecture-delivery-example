package com.delivery.data.db.jpa.cousine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaCousineRepositoryTest {

    @Autowired
    private JpaCousineRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @AutoConfigurationPackage
    static class Config {
    }

    @Test
    public void findByNameIgnoreCase() {
        // given
        Arrays.stream(new String[]{"aAbc", "abBc", "abCc"})
                .forEach(name -> entityManager.persistAndFlush(new CousineData(null, name)));

        // when
        final List<CousineData> cousines = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(cousines)
                .extracting("name")
                .containsOnly("aAbc", "abCc");
    }
}