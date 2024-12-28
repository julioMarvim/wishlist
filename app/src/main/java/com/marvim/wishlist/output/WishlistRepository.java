package com.marvim.wishlist.output;

import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;

public interface WishlistRepository {
    void save(String clientId, AddProductRequestOutput product);
    void remove(String clientId, String productId);
    void checkProductInWishlist(String clientId, String productId);
    WishlistResponseOutput findOrCreate(String clientId);
}
