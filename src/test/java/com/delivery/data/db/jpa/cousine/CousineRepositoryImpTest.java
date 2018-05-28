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
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        Identity id = cousine.getId();
        CousineData cousineData = CousineData.fromCousine(cousine);

        doReturn(Optional.of(cousineData))
                .when(jpaCousineRepository)
                .findById(id.getNumber());

        assertThat(cousineRepository.getByIdentity(id)).isEqualTo(Optional.of(cousine));
    }

    @Test
    public void getAllReturnsAllCousines() {
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineData cousineData = CousineData.fromCousine(cousine);

        doReturn(Collections.singletonList(cousineData))
                .when(jpaCousineRepository)
                .findAll();

        assertThat(cousineRepository.getAll()).containsOnly(cousine);
    }
}