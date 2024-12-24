package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private static final Logger logger = LoggerFactory.getLogger(WishlistRepositoryImpl.class);

    private final SpringDataWishlistRepository repository;

    @Override
    public void save(Wishlist wishlist) {
        logger.info("Starting database operation to save wishlist for client with ID: {}", wishlist.getClientId());
        repository.save(wishlist);
        logger.info("Wishlist with ID: {} successfully updated in database for customer with ID: {}", wishlist.getId(), wishlist.getClientId());
    }

    @Override
    public void remove(Wishlist wishlist, String productId) {
        logger.info("Starting database operation to remove product with ID: {} in wishlist from client with ID: {}", productId, wishlist.getClientId());

        List<Product> mutableProducts = new ArrayList<>(wishlist.getProducts());
        mutableProducts.removeIf(product -> product.getId().equals(productId));

        wishlist.setProducts(mutableProducts);
        repository.save(wishlist);

        logger.info("Success in removing product with ID: {} from wishlist with ID: {} from customer with ID: {}", productId, wishlist.getId(), wishlist.getClientId());
    }

    @Override
    public Optional<Wishlist> findByClientId(String clientId) {
        logger.info("Starting database operation to find wishlist for client with ID: {}", clientId);

        Optional<Wishlist> wishlist = repository.findByClientId(clientId);

        if (wishlist.isPresent()) {
            logger.info("Successfully retrieved wishlist with ID: {} from customer with ID: {}",wishlist.get().getId(), clientId);
        } else {
            logger.warn("Wishlist for client with ID: {} not found", clientId);
        }

        return wishlist;
    }
}
