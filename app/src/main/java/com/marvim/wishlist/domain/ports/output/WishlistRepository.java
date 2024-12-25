package com.marvim.wishlist.domain.ports.output;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;

import java.util.Optional;

public interface WishlistRepository {
    void save(String clientId, Product product);
    void remove(Wishlist wishlist, String productId);
    Optional<Wishlist> findByClientId(String clientId);
}
