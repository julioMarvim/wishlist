package com.marvim.wishlist.application.usecases;

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
    public void execute(String clientId, String productId) {
        logger.info("Starting operation to check if product with ID: {} exists in client {}'s wishlist", productId, clientId);
        wishlistRepository.checkProductInWishlist(clientId, productId);
    }
}
