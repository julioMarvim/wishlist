package com.marvim.wishlist.input;

import com.marvim.wishlist.input.dto.request.AddProductRequestInput;

public interface AddProductToWishlistUseCase {
    void execute(String clientId, AddProductRequestInput addProductRequestInput);
}
