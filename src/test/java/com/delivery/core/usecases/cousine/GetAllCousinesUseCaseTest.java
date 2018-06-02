package com.delivery.core.usecases.cousine;

import com.delivery.core.entities.TestCoreEntityGenerator;
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
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        GetAllCousinesUseCase.InputValues input =
                new GetAllCousinesUseCase.InputValues();

        // and
        doReturn(cousines)
                .when(repository)
                .getAll();

        // when
        final List<Cousine> actual = useCase.execute(input).getCousines();

        // then
        assertThat(actual).isEqualTo(cousines);
    }
}