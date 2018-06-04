package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.CustomerData;
import com.delivery.data.db.jpa.entities.OrderData;
import com.delivery.data.db.jpa.entities.OrderItemData;
import com.delivery.data.db.jpa.entities.ProductData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static com.delivery.TestEntityGenerator.randomAddress;
import static com.delivery.TestEntityGenerator.randomDescription;
import static com.delivery.TestEntityGenerator.randomEmail;
import static com.delivery.TestEntityGenerator.randomName;
import static com.delivery.TestEntityGenerator.randomPassword;
import static com.delivery.TestEntityGenerator.randomPrice;
import static com.delivery.core.entities.TestCoreEntityGenerator.randomQuantity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaOrderRepositoryTest {

    @Configuration
    @EntityScan("com.delivery.data.db.jpa.entities")
    @AutoConfigurationPackage
    static class Config {
    }

    @Autowired
    private JpaOrderRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveOrder() {
        // given
        CustomerData customerData = new CustomerData(null, randomName(), randomEmail(), randomAddress(), randomPassword());
        customerData = entityManager.persistFlushFind(customerData);

        //and
        CousineData cousineData = CousineData.newInstance(randomName());
        cousineData = entityManager.persistFlushFind(cousineData);

        // and
        StoreData storeData = new StoreData(null, randomName(), randomAddress(), cousineData, new HashSet<>());
        storeData = entityManager.persistFlushFind(storeData);

        // and
        ProductData productData = new ProductData(null, randomName(), randomDescription(), randomPrice(), storeData);
        productData = entityManager.persistAndFlush(productData);

        // and
        OrderItemData orderItemData = OrderItemData.newInstance(productData, randomQuantity());

        // and
        OrderData toBeSaved = OrderData.newInstance(
                customerData, storeData, new HashSet<>(singletonList(orderItemData))
        );

        // when
        OrderData savedOrder = repository.save(toBeSaved);

        // then
        assertThat(savedOrder.getId()).isNotNull();
    }
}