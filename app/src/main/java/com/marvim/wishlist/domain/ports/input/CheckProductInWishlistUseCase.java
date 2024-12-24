package com.marvim.wishlist.domain.ports.input;

public interface CheckProductInWishlistUseCase {
    void execute(String clientId, String productId);
}
