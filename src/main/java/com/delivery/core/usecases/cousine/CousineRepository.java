package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;

import java.util.List;
import java.util.Optional;

public interface CousineRepository {
    Optional<Cousine> getByIdentity(Identity id);

    List<Cousine> getAll();

    List<Cousine> searchByName(String search);
}
