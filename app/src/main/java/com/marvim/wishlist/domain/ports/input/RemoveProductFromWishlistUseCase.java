package com.marvim.wishlist.domain.ports.input;

import com.marvim.wishlist.domain.entity.Product;

public interface RemoveProductFromWishlistUseCase {
    void execute(String clientId, String productId);
}
