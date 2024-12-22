package com.marvim.wishlist.domain.ports.input;

public interface CheckProductInWishlistUseCase {
    boolean execute(String clientId, String productId);
}
