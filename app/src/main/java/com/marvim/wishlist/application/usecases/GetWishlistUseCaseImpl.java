package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.GetWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWishlistUseCaseImpl implements GetWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetWishlistUseCaseImpl.class);

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist execute(String clientId) {
        logger.info("Starting operation to fetch wishlist for client with ID: {}", clientId);

        return wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> {
                    logger.error("Wishlist for client with ID: {} not found", clientId);
                    return new WishlistNotFoundException(clientId);
                });
    }
}
