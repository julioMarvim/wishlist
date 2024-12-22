package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.ports.input.RemoveProductFromWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProductFromWishlistUseCaseImpl implements RemoveProductFromWishlistUseCase {

    private final WishlistRepository wishlistRepository;

    @Override
    public void execute(String clientId, String productId) { wishlistRepository.remove(clientId, productId); }
}