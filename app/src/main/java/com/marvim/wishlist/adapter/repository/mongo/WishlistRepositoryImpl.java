package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.entity.WishlistFactory;
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
    private static final int MAX_PRODUCTS = 20;

    @Override
    public void save(String clientId, Product product) {
        logger.info("Starting database operation to save wishlist for client with ID: {}", clientId);
        Wishlist wishlist = getOrCreateWishlist(clientId);
        validateWishlistLimit(wishlist);
        validateProductNotInWishlist(wishlist, product);
        wishlist.addProduct(product);
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

    private Wishlist getOrCreateWishlist(String clientId) {
        return repository.findByClientId(clientId)
                .orElseGet(() -> WishlistFactory.createNew(clientId));
    }

    private void validateWishlistLimit(Wishlist wishlist) {
        if (wishlist.getProducts().size() >= MAX_PRODUCTS) {
            logger.error("Wishlist for client with ID: {} exceeds the limit of {} products", wishlist.getClientId(), MAX_PRODUCTS);
            throw new WishlistLimitExceededException(wishlist.getClientId());
        }
    }

    private void validateProductNotInWishlist(Wishlist wishlist, Product product) {
        boolean exists = wishlist.getProducts().stream()
                .anyMatch(p -> p.getId().equals(product.getId()));
        if (exists) {
            logger.error("Product with ID: {} already exists in wishlist for client with ID: {}", product.getId(), wishlist.getClientId());
            throw new ProductAlreadyInWishlistException(wishlist.getClientId(), product.getId());
        }
    }
}
