package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

import static com.delivery.data.db.jpa.entities.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "product")
@EqualsAndHashCode(of = {"name", "description", "price"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "product")
@ToString(of = {"name", "description", "price"})
public class ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @DecimalMin(value = "0.00")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreData store;

    public static ProductData newInstance(String name, String description, StoreData storeData) {
        return new ProductData(
                null,
                name,
                description,
                0d,
                storeData);
    }

    public static ProductData from(Product product) {
        return new ProductData(
                convertId(product.getId()),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                StoreData.fromDomain(product.getStore()));
    }

    public static Product from(ProductData productData) {
        return new Product(
                new Identity(productData.getId()),
                productData.getName(),
                productData.getDescription(),
                productData.getPrice(),
                StoreData.toDomain(productData.getStore()));
    }
}
