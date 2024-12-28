package com.marvim.wishlist.usecases;

import com.marvim.wishlist.input.GetWishlistUseCase;
import com.marvim.wishlist.input.dto.response.WishlistResponseInput;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.usecases.mapper.WishlistToInputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetWishlistUseCaseImpl implements GetWishlistUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetWishlistUseCaseImpl.class);

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public WishlistResponseInput execute(String clientId) {
        logger.info("Starting operation to fetch wishlist for client with ID: {}", clientId);
        return WishlistToInputMapper.toInputDto(wishlistRepository.findOrCreate(clientId));
    }
}
