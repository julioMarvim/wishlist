package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.config.handler.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.config.handler.exception.WishlistLimitExceededException;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.entity.WishlistFactory;
import com.marvim.wishlist.domain.ports.output.WishlistRepository;
import com.marvim.wishlist.domain.service.WishlistValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddProductToWishlistUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private WishlistValidationService wishlistValidationService;

    @InjectMocks
    private AddProductToWishlistUseCaseImpl useCase;

    @Test
    void shouldSaveProductWhenExecutingUseCase() {
        String clientId = "client-id";
        Product product = Product.builder()
                .id("product-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        useCase.execute(clientId, product);

        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(wishlistRepository).save(clientIdCaptor.capture(), productCaptor.capture());

        assertEquals(clientId, clientIdCaptor.getValue());
        assertEquals(product, productCaptor.getValue());
    }
}

