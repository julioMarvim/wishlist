package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.ProductNotFoundException;
import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.RemoveProductFromWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemoveProductFromWishlistUseCaseImpl implements RemoveProductFromWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RemoveProductFromWishlistUseCaseImpl.class);

    private final WishlistRepository wishlistRepository;

    @Override
    public void execute(String clientId, String productId) {
        logger.info("Starting operation to remove product with ID: {} to wishlist for client with ID: {}", productId, clientId);

        Wishlist wishlist = wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> {
                    logger.error("Wishlist for client with ID: {} not found", clientId);
                    return new WishlistNotFoundException(clientId);
                });

        if (wishlist.getProducts().stream().noneMatch(product -> product.getId().equals(productId))) {
            logger.error("Product with ID: {} not found in wishlist for client with ID: {}", productId, clientId);
            throw new ProductNotFoundException(clientId, productId);
        }

        wishlistRepository.remove(wishlist, productId);
    }
}
