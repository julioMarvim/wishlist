package com.marvim.wishlist.usecases;

import com.marvim.wishlist.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.output.WishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckProductInWishlistUseCaseImpl implements CheckProductInWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CheckProductInWishlistUseCaseImpl.class);

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public void execute(String clientId, String productId) {
        logger.info("Starting operation to check if product with ID: {} exists in client {}'s wishlist", productId, clientId);
        wishlistRepository.checkProductInWishlist(clientId, productId);
    }
}
