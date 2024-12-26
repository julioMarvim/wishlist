package com.marvim.wishlist.input;

import com.marvim.wishlist.input.dto.response.WishlistResponseInputDto;

public interface GetWishlistUseCase {
    WishlistResponseInputDto execute(String clientId);
}
