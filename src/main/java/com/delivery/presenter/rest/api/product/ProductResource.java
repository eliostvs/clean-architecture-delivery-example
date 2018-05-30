package com.delivery.presenter.rest.api.product;

import com.delivery.presenter.rest.api.entities.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/Product")
public interface ProductResource {
    @GetMapping
    CompletableFuture<List<ProductResponse>> getAllProducts();

    @GetMapping("/{id}")
    CompletableFuture<ProductResponse> getByIdentity(@PathVariable Long id);

    @GetMapping("/search/{text}")
    CompletableFuture<List<ProductResponse>> getByMatchingName(@PathVariable String text);
}
