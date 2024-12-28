package com.marvim.wishlist.usecases;

import com.marvim.wishlist.usecases.mapper.AddProductToOutputMapper;
import com.marvim.wishlist.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.input.dto.request.AddProductRequestInput;
import com.marvim.wishlist.output.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddProductToWishlistUseCaseImpl implements AddProductToWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AddProductToWishlistUseCaseImpl.class);

    private final WishlistRepository wishlistRepository;

    @Override
    public void execute(String clientId, AddProductRequestInput addProductRequestInput) {
        logger.info("Starting operation to add product with ID: {} to wishlist for client with ID: {}", addProductRequestInput.getId(), clientId);
        wishlistRepository.save(clientId, AddProductToOutputMapper.toOutputDto(addProductRequestInput));
    }
}
