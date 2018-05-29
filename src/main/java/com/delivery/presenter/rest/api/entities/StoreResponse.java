package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Store;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class StoreResponse {
    private final Long id;
    private final String name;
    private final String address;
    private final Long cousineId;

    private static StoreResponse fromStore(Long cousineId, Store store) {
        return new StoreResponse(
                store.getId().getNumber(),
                store.getName(),
                store.getAddress(),
                cousineId
        );
    }

    // TODO: test
    public static Set<StoreResponse> fromStore(Long id, Set<Store> stores) {
        return stores
                .parallelStream()
                .map(store -> StoreResponse.fromStore(id, store))
                .collect(Collectors.toSet());
    }

}
