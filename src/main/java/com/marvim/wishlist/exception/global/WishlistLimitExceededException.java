package com.marvim.wishlist.exception.global;

public class WishlistLimitExceededException extends RuntimeException {
    public WishlistLimitExceededException(String clientId) {
        super(String.format("Customer ID %s has exceeded the maximum number of products in the wishlist.", clientId));
    }
}
