package com.delivery.presenter.rest.api.cousine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/Cousine")
public interface CousineResource {
    @GetMapping(value = "/{id}")
    CompletableFuture<CousineResponse> getCousineById(@PathVariable Long id);

    @GetMapping
    CompletableFuture<List<CousineResponse>> getAllCousines();
}
