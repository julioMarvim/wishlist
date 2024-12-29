package com.marvim.wishlist.usecases;

import com.marvim.wishlist.input.RemoveProductFromWishlistUseCase;
import com.marvim.wishlist.output.WishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveProductFromWishlistUseCaseImpl implements RemoveProductFromWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RemoveProductFromWishlistUseCaseImpl.class);

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public void execute(String clientId, String productId) {
        logger.info("Starting operation to remove product with ID: {} to wishlist for client with ID: {}", productId, clientId);
        wishlistRepository.remove(clientId, productId);
    }
}
