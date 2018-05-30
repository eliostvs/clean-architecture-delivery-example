package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Cousine;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class CousineResponse {
    private final Long id;
    private final String name;

    private static CousineResponse fromDomain(Cousine cousine) {
        return new CousineResponse(cousine.getId().getNumber(), cousine.getName());
    }
    
    public static List<CousineResponse> fromDomain(List<Cousine> cousines) {
        return cousines
                .stream()
                .map(CousineResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
