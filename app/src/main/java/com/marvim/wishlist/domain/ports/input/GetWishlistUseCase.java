package com.marvim.wishlist.domain.ports.input;

import com.marvim.wishlist.domain.entity.Wishlist;

public interface GetWishlistUseCase {
    Wishlist execute(String clientId);
}
