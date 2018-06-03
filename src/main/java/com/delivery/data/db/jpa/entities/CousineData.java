package com.delivery.data.db.jpa.entities;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static com.delivery.data.db.jpa.entities.IdConverter.convertId;

@AllArgsConstructor
@Entity(name = "cousine")
@EqualsAndHashCode(of = {"name"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "cousine")
@ToString(of = {"name"})
public class CousineData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "cousine",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<StoreData> stores;

    // TODO: test
    public void addStore(StoreData store) {
        if (this.stores == null) {
            this.stores = new HashSet<>();
        }

        store.setCousine(this);
        this.stores.add(store);
    }

    // TODO: test
    public static CousineData newInstance(String name) {
        return new CousineData(null, name, new HashSet<>());
    }

    // TODO: test
    public static CousineData from(Cousine cousine) {
        return new CousineData(
                convertId(cousine.getId()),
                cousine.getName(),
                new HashSet<>()
        );
    }

    public Cousine fromThis() {
        return new Cousine(
                new Identity(id),
                name
        );
    }
}
