package com.marvim.wishlist.usecases;

import com.marvim.wishlist.usecases.mapper.AddProductMapper;
import com.marvim.wishlist.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.input.dto.request.AddProductRequestInputDto;
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
    public void execute(String clientId, AddProductRequestInputDto addProductRequestInputDto) {
        logger.info("Starting operation to add product with ID: {} to wishlist for client with ID: {}", addProductRequestInputDto.getId(), clientId);
        wishlistRepository.save(clientId, AddProductMapper.toOutputDto(addProductRequestInputDto));
    }
}
