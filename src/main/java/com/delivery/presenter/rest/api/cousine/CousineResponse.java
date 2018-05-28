package com.delivery.presenter.rest.api.cousine;

import com.delivery.core.domain.Cousine;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class CousineResponse {
    private final Long id;
    private final String name;

    public static CousineResponse fromCousine(Cousine cousine) {
        return new CousineResponse(cousine.getId().getNumber(), cousine.getName());
    }
    
    public static List<CousineResponse> fromCousine(List<Cousine> cousines) {
        return cousines
                .stream()
                .map(CousineResponse::fromCousine)
                .collect(Collectors.toList());
    }
}
