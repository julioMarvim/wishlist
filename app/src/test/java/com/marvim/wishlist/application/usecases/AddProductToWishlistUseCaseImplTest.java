package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.ports.output.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddProductToWishlistUseCaseImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AddProductToWishlistUseCaseImpl addProductToWishlistUseCase;

    @Test
    void shouldSaveProductWhenExecutingUseCase() {
        String clientId = "1";
        Product product = Product.builder()
                .id("1")
                .name("Garrafa")
                .description("Garrafa de café")
                .price(30d)
                .build();

        addProductToWishlistUseCase.execute(clientId, product);

        verify(productRepository, Mockito.times(1)).save(product);
    }
}
