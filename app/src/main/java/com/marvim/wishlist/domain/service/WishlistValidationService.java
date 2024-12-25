package com.marvim.wishlist.domain.service;

import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
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
}