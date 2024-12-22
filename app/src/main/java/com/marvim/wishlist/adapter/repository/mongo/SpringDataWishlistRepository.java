package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.domain.entity.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataWishlistRepository extends MongoRepository<Wishlist, String> {
    Optional<Wishlist> findByClientId(String clientId);
}
