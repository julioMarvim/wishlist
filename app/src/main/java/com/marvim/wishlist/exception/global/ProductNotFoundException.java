package com.marvim.wishlist.exception.global;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String clientId, String productId) {
        super(String.format("Product %s not found in customer wishlist with id: %s",productId, clientId));
    }
}
