package com.delivery.data.db.jpa.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Cousine;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

    @Column(unique = true)
    @NotNull
    private String name;

    public static Cousine toCousine(CousineData cousineData) {
        return new Cousine(new Identity(cousineData.getId()), cousineData.getName());
    }

    public static CousineData fromCousine(Cousine cousine) {
        final Long id = cousine.getId() != null ? cousine.getId().getNumber() : null;
        return new CousineData(id, cousine.getName());
    }
}
