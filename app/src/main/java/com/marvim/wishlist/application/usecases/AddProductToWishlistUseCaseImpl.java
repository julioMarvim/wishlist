package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AddProductToWishlistUseCaseImpl implements AddProductToWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AddProductToWishlistUseCaseImpl.class);

    private final WishlistRepository wishlistRepository;
    private static final int MAX_PRODUCTS = 20;

    @Override
    public void execute(String clientId, Product product) {
        logger.info("Starting operation to add product with ID: {} to wishlist for client with ID: {}", product.getId(), clientId);

        Wishlist wishlist = wishlistRepository.findByClientId(clientId)
                .orElseGet(() -> Wishlist.builder()
                        .clientId(clientId)
                        .products(new ArrayList<>())
                        .build());

        if (wishlist.getProducts().size() >= MAX_PRODUCTS) {
            logger.error("Wishlist for client with ID: {} exceeds the limit of {} products", clientId, MAX_PRODUCTS);
            throw new WishlistLimitExceededException(clientId);
        }

        if (wishlist.getProducts().stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            logger.error("Product with ID: {} already exists in wishlist for client with ID: {}", product.getId(), clientId);
            throw new ProductAlreadyInWishlistException(clientId, product.getId());
        }

        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
    }
}
