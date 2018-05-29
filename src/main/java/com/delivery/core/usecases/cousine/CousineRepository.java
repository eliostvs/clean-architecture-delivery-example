package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Store;

import java.util.List;
import java.util.Set;

public interface CousineRepository {
    Set<Store> getStoresByIdentity(Identity id);

    List<Cousine> getAll();

    List<Cousine> searchByName(String search);
}
