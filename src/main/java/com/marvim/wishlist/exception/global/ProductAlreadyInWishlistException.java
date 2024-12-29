package com.marvim.wishlist.exception.global;

public class ProductAlreadyInWishlistException extends RuntimeException {
    public ProductAlreadyInWishlistException(String clientId, String productId) {
        super(String.format("Product with ID %s is already in the customer id %s wishlist.", productId, clientId));
    }
}
