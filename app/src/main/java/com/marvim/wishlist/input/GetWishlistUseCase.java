package com.marvim.wishlist.input;

import com.marvim.wishlist.input.dto.response.WishlistResponseInput;

public interface GetWishlistUseCase {
    WishlistResponseInput execute(String clientId);
}
