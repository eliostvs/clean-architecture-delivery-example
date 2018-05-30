package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Store;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity(name = "store")
@EqualsAndHashCode(of = {"name", "address"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "store")
@ToString(of = {"name", "address"})
public class StoreData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @ManyToOne
    @JoinColumn(name = "cousine_id", nullable = false)
    private CousineData cousine;

    @OneToMany(mappedBy = "store")
    private Set<ProductData> products;

    // TODO: test method
    public static Store toDomain(StoreData storeData) {
        return new Store(
                new Identity(storeData.getId()),
                storeData.getName(),
                storeData.getAddress(),
                CousineData.toDomain(storeData.getCousine())
        );
    }

    // TODO: test method
    public static StoreData fromDomain(Store store) {
        return new StoreData(
                store.getId().getNumber(),
                store.getName(),
                store.getAddress(),
                CousineData.fromDomain(store.getCousine()),
                new HashSet<>());
    }

    public static StoreData newInstance(String name, CousineData cousineData) {
        return new StoreData(null, name, "Address of " + name, cousineData, new HashSet<>());
    }
}
