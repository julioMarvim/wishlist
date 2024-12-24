package com.marvim.wishlist.config.exception;

public class ProductAlreadyInWishlistException extends RuntimeException {
    public ProductAlreadyInWishlistException(String clientId, String productId) {
        super(String.format("Product with ID %s is already in the customer id %s wishlist", productId, clientId));
    }
}
