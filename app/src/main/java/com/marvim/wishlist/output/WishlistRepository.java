package com.marvim.wishlist.output;

import com.marvim.wishlist.output.dto.response.WishlistResponseOutputDto;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;

public interface WishlistRepository {
    void save(String clientId, AddProductRequestOutputDto product);
    void remove(String clientId, String productId);
    void checkProductInWishlist(String clientId, String productId);
    WishlistResponseOutputDto findByClientId(String clientId);
}
