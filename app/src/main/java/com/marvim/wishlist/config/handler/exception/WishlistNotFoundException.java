package com.marvim.wishlist.config.handler.exception;

public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(String clientId) {
        super(String.format("Wishlist not found for cilent: %s",clientId));
    }
}
