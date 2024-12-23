package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddProductToWishlistUseCaseImpl implements AddProductToWishlistUseCase {

    private final WishlistRepository wishlistRepository;
    private static final int MAX_PRODUCTS = 20;

    @Override
    public void execute(String clientId, Product product) {
        Wishlist wishlist = wishlistRepository.findByClientId(clientId)
                .orElseGet(() -> Wishlist.builder()
                        .clientId(clientId)
                        .products(new ArrayList<>())
                        .build());

        if (wishlist.getProducts().size() >= MAX_PRODUCTS) {
            throw new IllegalStateException("A wishlist já contém o número máximo de produtos.");
        }

        if (wishlist.getProducts().stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new ProductAlreadyInWishlistException(clientId, product.getId());
        }

        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
    }
}
