package com.marvim.wishlist.adapter.repository.mongo;

import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.ProductNotFoundException;
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

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private static final Logger logger = LoggerFactory.getLogger(WishlistRepositoryImpl.class);

    private final SpringDataWishlistRepository repository;
    private static final int MAX_PRODUCTS = 20;

    @Override
    public void save(String clientId, Product product) {
        logger.info("Starting database operation to save wishlist for client with ID: {}", clientId);
        Wishlist wishlist = repository.findByClientId(clientId)
                .orElseGet(() -> WishlistFactory.createNew(clientId));

        validateWishlistLimit(wishlist);
        validateProductNotInWishlist(wishlist, product);
        wishlist.addProduct(product);
        repository.save(wishlist);
        logger.info("Wishlist with ID: {} successfully updated in database for customer with ID: {}", wishlist.getId(), wishlist.getClientId());
    }

    @Override
    public void remove(String clientId, String productId) {
        logger.info("Starting database operation to remove product with ID: {} in wishlist from client with ID: {}", productId, clientId);
        Wishlist wishlist = getWishlist(clientId);
        wishlist.removeProduct(productId);
        repository.save(wishlist);
        logger.info("Success in removing product with ID: {} from wishlist with ID: {} from customer with ID: {}", productId, wishlist.getId(), wishlist.getClientId());
    }

    @Override
    public Wishlist findByClientId(String clientId) {
        logger.info("Starting database operation to find wishlist for client with ID: {}", clientId);
        Wishlist wishlist = getWishlist(clientId);
        logger.info("Successfully retrieved wishlist with ID: {} from customer with ID: {}", wishlist.getId(), clientId);
        return wishlist;
    }

    public void checkProductInWishlist(String clientId, String productId) {
        logger.info("Starting database operation to check if product with ID: {} exists in wishlist for client with ID: {}", productId, clientId);
        Wishlist wishlist = getWishlist(clientId);
        validateProductInWhishlist(clientId, productId, wishlist);
    }

    private Wishlist getWishlist(String clientId) {
        return repository.findByClientId(clientId)
                .orElseThrow(() -> {
                    logger.error("Wishlist for client with ID: {} not found", clientId);
                    return new WishlistNotFoundException(clientId);
                });
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

    private static void validateProductInWhishlist(String clientId, String productId, Wishlist wishlist) {
        if (wishlist.getProducts().stream().noneMatch(product -> product.getId().equals(productId))) {
            logger.error("Product with ID: {} not found in wishlist for client with ID: {}", productId, clientId);
            throw new ProductNotFoundException(clientId, productId);
        }
    }
}
