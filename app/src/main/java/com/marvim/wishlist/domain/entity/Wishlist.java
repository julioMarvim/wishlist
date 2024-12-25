package com.marvim.wishlist.domain.entity;

import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.ProductNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Getter
@Document(collection = "wishlists")
public class Wishlist {
    @Id
    private String id;
    private String clientId;
    private List<Product> products;

    public void addProduct(Product product) {
        if (products.contains(product)) {
            throw new ProductAlreadyInWishlistException(this.getClientId(), product.getId());
        }
        products.add(product);
    }

    public void removeProduct(Product product) {
        if (!products.contains(product)) {
            throw new ProductNotFoundException(this.getClientId(), product.getId());
        }
        products.remove(product);
    }
}
