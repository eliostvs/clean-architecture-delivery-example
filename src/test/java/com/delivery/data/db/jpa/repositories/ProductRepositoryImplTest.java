package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.data.db.jpa.entities.ProductData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryImplTest {

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Mock
    private JpaProductRepository jpaProductRepository;

    @Test
    public void findProductsByStoreAndProductsIdReturnListOfProducts() {
        // given
        Identity storeId = TestCoreEntityGenerator.randomId();
        final Identity productId = TestCoreEntityGenerator.randomId();

        // and
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductData productData = ProductData.from(product);

        doReturn(singletonList(productData))
                .when(jpaProductRepository)
                .findByStoreIdAndIdIsIn(eq(storeId.getNumber()), eq(singletonList(productId.getNumber())));

        // when
        List<Product> actual = productRepository.searchProductsByStoreAndProductsId(storeId, singletonList(productId));

        // then
        assertThat(actual).isEqualTo(singletonList(product));
    }

    @Test
    public void searchByNameReturnMatchingProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductData productData = ProductData.from(product);
        String searchText = "abc";

        // and
        doReturn(singletonList(productData))
                .when(jpaProductRepository)
                .findByNameContainingOrDescriptionContainingAllIgnoreCase(searchText, searchText);

        // when
        List<Product> actual = productRepository.searchByNameOrDescription(searchText);

        // then
        assertThat(actual).containsOnly(product);
    }

    @Test
    public void getByIdentityReturnsEmpty() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();

        // and
        doReturn(Optional.empty())
                .when(jpaProductRepository)
                .findById(id.getNumber());

        // when
        Optional<Product> actual = productRepository.getById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    public void getByIdentityReturnsProduct() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        Identity id = product.getId();
        ProductData productData = ProductData.from(product);

        // and
        doReturn(Optional.of(productData))
                .when(jpaProductRepository)
                .findById(id.getNumber());

        // when
        Optional<Product> actual = productRepository.getById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(product);
    }

    @Test
    public void getAllReturnsAllProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductData productData = ProductData.from(product);

        // and
        doReturn(singletonList(productData))
                .when(jpaProductRepository)
                .findAll();

        // when
        List<Product> actual = productRepository.getAll();

        // then
        assertThat(actual).containsOnly(product);
    }
}