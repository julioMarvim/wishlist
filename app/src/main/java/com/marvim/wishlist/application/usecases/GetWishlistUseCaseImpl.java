package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.GetWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWishlistUseCaseImpl implements GetWishlistUseCase {

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist execute(String clientId) {
        return wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for clientId: " + clientId));
    }
}
