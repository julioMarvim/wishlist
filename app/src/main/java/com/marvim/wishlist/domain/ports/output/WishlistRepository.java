package com.marvim.wishlist.domain.ports.output;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;

import java.util.Optional;

public interface WishlistRepository {
    void save(Wishlist wishlist);
    void remove(String clientId, String productId);
    Optional<Wishlist> findByClientId(String clientId);
}
