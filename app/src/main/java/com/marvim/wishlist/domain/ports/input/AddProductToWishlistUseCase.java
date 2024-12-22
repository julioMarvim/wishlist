package com.marvim.wishlist.domain.ports.input;

import com.marvim.wishlist.domain.entity.Product;

public interface AddProductToWishlistUseCase {
    void execute(String clientId, Product product);
}
