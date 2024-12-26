package com.marvim.wishlist.application.usecases;

import com.marvim.wishlist.input.dto.request.AddProductRequestInputDto;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;
import com.marvim.wishlist.usecases.AddProductToWishlistUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddProductEntityToWishlistEntityUseCaseImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private AddProductToWishlistUseCaseImpl useCase;

    @Test
    void shouldSaveProductWhenExecutingUseCase() {
        String clientId = "client-id";
        AddProductRequestInputDto productyEntity = AddProductRequestInputDto.builder()
                .id("productyEntity-id")
                .name("Garrafa")
                .description("Garrafa de café")
                .build();

        useCase.execute(clientId, productyEntity);

        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<AddProductRequestOutputDto> productCaptor = ArgumentCaptor.forClass(AddProductRequestOutputDto.class);
        verify(wishlistRepository).save(clientIdCaptor.capture(), productCaptor.capture());

        assertEquals(clientId, clientIdCaptor.getValue());
        assertEquals(productyEntity.getId(), productCaptor.getValue().getId());
        assertEquals(productyEntity.getName(), productCaptor.getValue().getName());
        assertEquals(productyEntity.getDescription(), productCaptor.getValue().getDescription());
    }
}

