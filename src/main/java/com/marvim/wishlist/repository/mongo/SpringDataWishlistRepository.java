package com.marvim.wishlist.repository.mongo;

import com.marvim.wishlist.repository.entity.WishlistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataWishlistRepository extends MongoRepository<WishlistEntity, String> {
    Optional<WishlistEntity> findByClientId(String clientId);
}
