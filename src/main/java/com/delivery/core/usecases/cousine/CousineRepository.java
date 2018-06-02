package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Store;

import java.util.List;

public interface CousineRepository {
    List<Store> getStoresById(Identity id);

    List<Cousine> getAll();

    List<Cousine> searchByName(String search);
}
