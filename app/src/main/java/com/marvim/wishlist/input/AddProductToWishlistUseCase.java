package com.marvim.wishlist.input;

import com.marvim.wishlist.input.dto.request.AddProductRequestInputDto;

public interface AddProductToWishlistUseCase {
    void execute(String clientId, AddProductRequestInputDto addProductRequestInputDto);
}
