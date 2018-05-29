package com.delivery.core.usecases.store;

import com.delivery.core.domain.Store;

import java.util.List;

public interface StoreRepository {
    List<Store> getAll();

    List<Store> searchByName(String searchText);
}
