package com.marvim.wishlist.domain.ports.output;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;

public interface WishlistRepository {
    void save(String clientId, Product product);
    void remove(String clientId, String productId);
    void checkProductInWishlist(String clientId, String productId);
    Wishlist findByClientId(String clientId);
}
