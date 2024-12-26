package com.marvim.wishlist.input.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String clientId, String productId) {
        super(String.format("ProductEntity %s not found in customer wishlist with id: %s",productId, clientId));
    }
}