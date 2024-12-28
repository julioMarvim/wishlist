package com.marvim.wishlist.repository.entity;

import com.marvim.wishlist.exception.global.ProductNotFoundException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "wishlists")
public record WishlistEntity(
        @Id String id,
        String clientId,
        List<ProductEntity> products
) {

    public WishlistEntity removeProduct(String productId) {
        ProductEntity productEntityToRemove = products.stream()
                .filter(product -> product.id().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(clientId, productId));

        List<ProductEntity> newProducts = products.stream()
                .filter(product -> !product.id().equals(productId))
                .collect(Collectors.toList());

        return new WishlistEntity(id, clientId, newProducts);
    }
}

