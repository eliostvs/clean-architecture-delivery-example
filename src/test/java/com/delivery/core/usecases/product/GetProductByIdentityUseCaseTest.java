package com.delivery.core.usecases.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
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
public class GetProductByIdentityUseCaseTest {

    @InjectMocks
    private GetProductByIdentityUseCase useCase;

    @Mock
    private ProductRepository repository;

    @Test
    public void executeThrowsException() {
        // given
        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getByIdentity(id);

        // then
        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No product found by identity: " + id.getNumber());
    }

    @Test
    public void executeReturnsProduct() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();

        // and
        doReturn(Optional.of(product))
                .when(repository)
                .getByIdentity(product.getId());

        // when
        Product actual = useCase.execute(product.getId());

        // then
        assertThat(actual).isEqualTo(product);
    }
}