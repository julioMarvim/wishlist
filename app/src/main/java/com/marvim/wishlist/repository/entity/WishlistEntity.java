package com.marvim.wishlist.repository.entity;

import com.marvim.wishlist.input.exception.ProductNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Document(collection = "wishlists")
public class WishlistEntity {
    @Id
    private String id;
    private String clientId;
    private List<ProductEntity> products;

    public void addProduct(ProductEntity productEntity) {
        products.add(productEntity);
    }

    public void removeProduct(String productId) {
        ProductEntity productEntityToRemove = products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(this.getClientId(), productId));

        products.remove(productEntityToRemove);
    }
}
