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

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetProductsByStoreAndProductsIdUseCaseTest {

    @InjectMocks
    private GetProductsByStoreAndProductsIdUseCase useCase;

    @Mock
    private ProductRepository repository;

    @Test
    public void executeReturnsOutputValues() {
        // given
        Identity storeId = TestCoreEntityGenerator.randomId();
        List<Identity> productsId = Collections.singletonList(TestCoreEntityGenerator.randomId());
        GetProductsByStoreAndProductsIdUseCase.InputValues inputValues =
                new GetProductsByStoreAndProductsIdUseCase.InputValues(storeId, productsId);

        // and
        List<Product> products = singletonList(TestCoreEntityGenerator.randomProduct());

        doReturn(products)
                .when(repository)
                .searchProductsByStoreAndProductsId(eq(storeId), eq(productsId));

        // when
        GetProductsByStoreAndProductsIdUseCase.OutputValues actual = useCase.execute(inputValues);

        // then
        assertThat(actual.getProducts()).isEqualTo(products);
    }

    @Test
    public void executeThrowExceptionWhenAndProductIsNotFound() {
        // given
        Identity storeId = TestCoreEntityGenerator.randomId();
        Identity productId = TestCoreEntityGenerator.randomId();
        GetProductsByStoreAndProductsIdUseCase.InputValues inputValues =
                new GetProductsByStoreAndProductsIdUseCase.InputValues(storeId, singletonList(productId));

        // and
        doReturn(Collections.emptyList())
                .when(repository)
                .searchProductsByStoreAndProductsId(eq(storeId), eq(singletonList(productId)));

        // then
        assertThatThrownBy(() -> useCase.execute(inputValues))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product(s) " + productId.getNumber() + " not found");
    }
}