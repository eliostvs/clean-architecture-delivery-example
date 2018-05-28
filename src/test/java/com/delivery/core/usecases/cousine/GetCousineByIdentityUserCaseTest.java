package com.delivery.core.usecases.cousine;

import com.delivery.core.TestCoreEntityGenerator;
import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetCousineByIdentityUserCaseTest {

    @InjectMocks
    private GetCousineByIdentityUserCase userCase;

    @Mock
    private CousineRepository repository;

    @Test
    public void returnsCousineWhenCousineIdIsFound() {
        Cousine cousine = TestCoreEntityGenerator.randomCousine();

        doReturn(Optional.of(cousine))
                .when(repository)
                .getByIdentity(cousine.getId());

        assertThat(userCase.execute(cousine.getId())).isEqualTo(cousine);
    }

    @Test
    public void throwsExceptionWhenCousineIdIsNotFound() {
        Identity id = TestCoreEntityGenerator.randomIdentity();

        doReturn(Optional.empty())
                .when(repository)
                .getByIdentity(id);

        assertThatThrownBy(() -> userCase.execute(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No cousine found for identity: " + id.getNumber());
    }
}