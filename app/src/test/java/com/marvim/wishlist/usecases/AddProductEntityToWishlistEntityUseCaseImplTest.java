package com.marvim.wishlist.usecases;

import com.marvim.wishlist.input.dto.request.AddProductRequestInput;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
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
        AddProductRequestInput productyEntity = new AddProductRequestInput("productyEntity-id", "name", "description");
        useCase.execute(clientId, productyEntity);

        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<AddProductRequestOutput> productCaptor = ArgumentCaptor.forClass(AddProductRequestOutput.class);
        verify(wishlistRepository).save(clientIdCaptor.capture(), productCaptor.capture());

        assertEquals(clientId, clientIdCaptor.getValue());
        assertEquals(productyEntity.id(), productCaptor.getValue().id());
        assertEquals(productyEntity.name(), productCaptor.getValue().name());
        assertEquals(productyEntity.description(), productCaptor.getValue().description());
    }
}

