package com.marvim.wishlist.config.exception;

public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(String clientId) {
        super(String.format("Wishlist not found for cilent: %s",clientId));
    }
}
