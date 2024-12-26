package com.marvim.wishlist.input;

public interface RemoveProductFromWishlistUseCase {
    void execute(String clientId, String productId);
}
