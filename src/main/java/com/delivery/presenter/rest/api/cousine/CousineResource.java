package com.delivery.presenter.rest.api.cousine;

import com.delivery.presenter.rest.api.entities.CousineResponse;
import com.delivery.presenter.rest.api.entities.StoreResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/Cousine")
public interface CousineResource {
    @GetMapping("/{id}/stores")
    CompletableFuture<List<StoreResponse>> getStoresByCousineId(@PathVariable Long id);

    @GetMapping
    CompletableFuture<List<CousineResponse>> getAllCousines();

    @GetMapping("/search/{text}")
    CompletableFuture<List<CousineResponse>> getAllCousinesByNameMatching(@PathVariable String text);
}
