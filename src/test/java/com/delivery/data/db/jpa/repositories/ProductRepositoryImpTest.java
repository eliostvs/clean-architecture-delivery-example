package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.data.db.jpa.entities.ProductData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryImpTest {

    @InjectMocks
    private ProductRepositoryImp productRepository;

    @Mock
    private JpaProductRepository jpaProductRepository;

    @Test
    public void getAllReturnsAllProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductData productData = ProductData.fromDomain(product);

        // and
        doReturn(Collections.singletonList(productData))
                .when(jpaProductRepository)
                .findAll();

        // when
        List<Product> actual = productRepository.getAll();

        // then
        assertThat(actual).containsOnly(product);
    }
}