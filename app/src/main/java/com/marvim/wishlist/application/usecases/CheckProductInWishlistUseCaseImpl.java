package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckProductInWishlistUseCaseImpl implements CheckProductInWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CheckProductInWishlistUseCaseImpl.class);

    private final WishlistRepository wishlistRepository;

    @Override
    public boolean execute(String clientId, String productId) {
        logger.info("Starting operation to check if product with ID: {} exists in client {}'s wishlist", productId, clientId);

        boolean exists = wishlistRepository.findByClientId(clientId)
                .map(wishlist -> wishlist.getProducts().stream()
                        .anyMatch(product -> product.getId().equals(productId)))
                .orElse(false);

        if (exists) {
            logger.info("Product with ID: {} exists in client {}'s wishlist", productId, clientId);
        } else {
            logger.info("Product with ID: {} does not exist in client {}'s wishlist", productId, clientId);
        }

        return exists;
    }
}
