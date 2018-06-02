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
public class SearchCousineByNameUseCaseTest {

    @Mock
    private CousineRepository repository;

    @InjectMocks
    private SearchCousineByNameUseCase useCase;

    @Test
    public void searchCousineByName() {
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        String searchText = "abc";
        SearchCousineByNameUseCase.InputValues input =
                new SearchCousineByNameUseCase.InputValues(searchText);

        // and
        doReturn(cousines)
                .when(repository)
                .searchByName(searchText);

        // when
        final List<Cousine> actual = useCase.execute(input).getCousines();

        // then
        assertThat(actual).isEqualTo(cousines);
    }
}