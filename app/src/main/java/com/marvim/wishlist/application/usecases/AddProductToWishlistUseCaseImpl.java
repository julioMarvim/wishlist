package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.entity.WishlistFactory;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import com.marvim.wishlist.domain.service.WishlistValidationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddProductToWishlistUseCaseImpl implements AddProductToWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AddProductToWishlistUseCaseImpl.class);

    private final WishlistRepository wishlistRepository;
    private final WishlistValidationService wishlistValidationService;

    @Override
    public void execute(String clientId, Product product) {
        logger.info("Starting operation to add product with ID: {} to wishlist for client with ID: {}", product.getId(), clientId);

        Wishlist wishlist = wishlistRepository.findByClientId(clientId)
                .orElseGet(() -> WishlistFactory.createNew(clientId));

        wishlistValidationService.validateWishlistLimit(wishlist);
        wishlist.addProduct(product);

        wishlistRepository.save(wishlist);
    }
}
