package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Store;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

import static com.delivery.data.db.jpa.entities.IdConverter.convertId;

@Value
public class StoreResponse {
    private final Long id;
    private final String name;
    private final String address;
    private final Long cousineId;

    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getId().getNumber(),
                store.getName(),
                store.getAddress(),
                convertId(store.getCousine().getId())
        );
    }

    public static List<StoreResponse> from(List<Store> stores) {
        return stores
                .parallelStream()
                .map(StoreResponse::from)
                .collect(Collectors.toList());
    }
}
