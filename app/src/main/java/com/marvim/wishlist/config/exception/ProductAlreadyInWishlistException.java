package com.marvim.wishlist.config.exception;

public class ProductAlreadyInWishlistException extends RuntimeException {
    public ProductAlreadyInWishlistException(String clientId, String productId) {
        super("Produto com ID " + productId + " já está na wishlist do cliente: " + clientId);
    }
}
