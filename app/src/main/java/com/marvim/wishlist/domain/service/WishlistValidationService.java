package com.marvim.wishlist.domain.service;

import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WishlistValidationService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistValidationService.class);
    private static final int MAX_PRODUCTS = 20;

    public void validateWishlistLimit(Wishlist wishlist) {
        if (wishlist.getProducts().size() >= MAX_PRODUCTS) {
            logger.error("Wishlist for client with ID: {} exceeds the limit of {} products", wishlist.getClientId(), MAX_PRODUCTS);
            throw new WishlistLimitExceededException(wishlist.getClientId());
        }
    }

    public void validateProductNotInWishlist(Wishlist wishlist, Product product) {
        boolean exists = wishlist.getProducts().stream()
                .anyMatch(p -> p.getId().equals(product.getId()));
        if (exists) {
            logger.error("Product with ID: {} already exists in wishlist for client with ID: {}", product.getId(), wishlist.getClientId());
            throw new ProductAlreadyInWishlistException(wishlist.getClientId(), product.getId());
        }
    }
}