package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Store;
import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.delivery.data.db.jpa.entities.IdConverter.convertId;

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

    private static StoreResponse fromStore(Store store) {
        return new StoreResponse(
                store.getId().getNumber(),
                store.getName(),
                store.getAddress(),
                convertId(store.getCousine().getId())
        );
    }

    public static List<StoreResponse> fromStore(List<Store> stores) {
        return stores
                .parallelStream()
                .map(StoreResponse::fromStore)
                .collect(Collectors.toList());
    }
}
