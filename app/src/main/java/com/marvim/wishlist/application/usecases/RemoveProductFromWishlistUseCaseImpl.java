package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.ProductNotFoundException;
import com.marvim.wishlist.config.handler.exception.WishlistNotFoundException;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.RemoveProductFromWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemoveProductFromWishlistUseCaseImpl implements RemoveProductFromWishlistUseCase {

    private final WishlistRepository wishlistRepository;

    @Override
    public void execute(String clientId, String productId) {
        Wishlist wishlist = wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> new WishlistNotFoundException(clientId));

        if (wishlist.getProducts().stream().noneMatch(product -> product.getId().equals(productId))) {
            throw new ProductNotFoundException(clientId, productId);
        }

        wishlistRepository.remove(wishlist, productId);
    }
}