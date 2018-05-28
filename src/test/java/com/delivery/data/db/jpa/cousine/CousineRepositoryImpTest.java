package com.delivery.data.db.jpa.cousine;

import com.delivery.core.TestCoreEntityGenerator;
import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
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
public class CousineRepositoryImpTest {

    @InjectMocks
    private CousineRepositoryImp cousineRepository;

    @Mock
    private JpaCousineRepository jpaCousineRepository;

    @Test
    public void getByIdentityReturnsOptionalCousine() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        Identity id = cousine.getId();
        CousineData cousineData = CousineData.fromCousine(cousine);

        // and
        doReturn(Optional.of(cousineData))
                .when(jpaCousineRepository)
                .findById(id.getNumber());

        // when
        final Optional<Cousine> actual = cousineRepository.getByIdentity(id);

        // then
        assertThat(actual).isEqualTo(Optional.of(cousine));
    }

    @Test
    public void getAllReturnsAllCousines() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineData cousineData = CousineData.fromCousine(cousine);

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
        CousineData cousineData = CousineData.fromCousine(cousine);
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