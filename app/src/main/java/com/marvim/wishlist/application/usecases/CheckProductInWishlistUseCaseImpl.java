package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckProductInWishlistUseCaseImpl implements CheckProductInWishlistUseCase {

    private final WishlistRepository wishlistRepository;

    @Override
    public boolean execute(String clientId, String productId) {
        return wishlistRepository.findByClientId(clientId)
                .map(wishlist -> wishlist.getProducts().stream()
                        .anyMatch(product -> product.getId().equals(productId)))
                .orElse(false);
    }

}
