package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.output.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddProductToWishlistUseCaseImpl implements AddProductToWishlistUseCase {

    private final ProductRepository productRepository;

    @Override
    public void execute(Product product) {
        productRepository.save(product);
    }
}
