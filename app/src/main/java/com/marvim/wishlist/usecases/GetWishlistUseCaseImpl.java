package com.marvim.wishlist.usecases;

import com.marvim.wishlist.input.GetWishlistUseCase;
import com.marvim.wishlist.input.dto.response.WishlistResponseInputDto;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.usecases.mapper.WishlistToInputMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWishlistUseCaseImpl implements GetWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetWishlistUseCaseImpl.class);

    private final WishlistRepository wishlistRepository;

    @Override
    public WishlistResponseInputDto execute(String clientId) {
        logger.info("Starting operation to fetch wishlist for client with ID: {}", clientId);
        return WishlistToInputMapper.toInputDto(wishlistRepository.findOrCreate(clientId));
    }
}
