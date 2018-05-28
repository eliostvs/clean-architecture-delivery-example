package com.delivery.core.usecases.cousine;

import com.delivery.core.TestCoreEntityGenerator;
import com.delivery.core.domain.Cousine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetAllCousinesUseCaseTest {

    @InjectMocks
    private GetAllCousinesUseCase useCase;

    @Mock
    private CousineRepository repository;

    @Test
    public void returnsAllCousines() {
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();

        doReturn(cousines)
                .when(repository)
                .getAll();

        assertThat(useCase.execute(null)).isEqualTo(cousines);
    }
}