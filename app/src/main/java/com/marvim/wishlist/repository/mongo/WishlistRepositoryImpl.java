package com.marvim.wishlist.repository.mongo;

import com.marvim.wishlist.output.dto.response.WishlistResponseOutputDto;
import com.marvim.wishlist.repository.entity.WishlistEntity;
import com.marvim.wishlist.repository.mapper.AddProductMapper;
import com.marvim.wishlist.input.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.input.exception.ProductNotFoundException;
import com.marvim.wishlist.input.exception.WishlistLimitExceededException;
import com.marvim.wishlist.input.exception.WishlistNotFoundException;
import com.marvim.wishlist.repository.entity.WishlistFactory;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;
import com.marvim.wishlist.repository.mapper.WishlistResponseDtoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private static final Logger logger = LoggerFactory.getLogger(WishlistRepositoryImpl.class);

    private final SpringDataWishlistRepository repository;
    private static final int MAX_PRODUCTS = 20;

    @Override
    public void save(String clientId, AddProductRequestOutputDto addProductRequestOutputDto) {
        logger.info("Starting database operation to save wishlistEntity for client with ID: {}", clientId);
        WishlistEntity wishlistEntity = repository.findByClientId(clientId)
                .orElseGet(() -> WishlistFactory.createNew(clientId));

        validateWishlistLimit(wishlistEntity);
        validateProductNotInWishlist(wishlistEntity, addProductRequestOutputDto);
        wishlistEntity.addProduct(AddProductMapper.toEntity(addProductRequestOutputDto));
        repository.save(wishlistEntity);
        logger.info("WishlistEntity with ID: {} successfully updated in database for customer with ID: {}", wishlistEntity.getId(), wishlistEntity.getClientId());
    }

    @Override
    public void remove(String clientId, String productId) {
        logger.info("Starting database operation to remove product with ID: {} in wishlistEntity from client with ID: {}", productId, clientId);
        WishlistEntity wishlistEntity = getWishlist(clientId);
        wishlistEntity.removeProduct(productId);
        repository.save(wishlistEntity);
        logger.info("Success in removing product with ID: {} from wishlistEntity with ID: {} from customer with ID: {}", productId, wishlistEntity.getId(), wishlistEntity.getClientId());
    }

    @Override
    public WishlistResponseOutputDto findByClientId(String clientId) {
        logger.info("Starting database operation to find wishlistEntity for client with ID: {}", clientId);
        WishlistResponseOutputDto wishlistResponseOutputDto = WishlistResponseDtoMapper.toOutputDto(getWishlist(clientId));
        logger.info("Successfully retrieved wishlistEntity with ID: {} from customer with ID: {}", wishlistResponseOutputDto.getId(), clientId);
        return wishlistResponseOutputDto;
    }

    public void checkProductInWishlist(String clientId, String productId) {
        logger.info("Starting database operation to check if product with ID: {} exists in wishlistEntity for client with ID: {}", productId, clientId);
        WishlistEntity wishlistEntity = getWishlist(clientId);
        validateProductInWhishlist(clientId, productId, wishlistEntity);
    }

    private WishlistEntity getWishlist(String clientId) {
        return repository.findByClientId(clientId)
                .orElseThrow(() -> {
                    logger.error("WishlistEntity for client with ID: {} not found", clientId);
                    return new WishlistNotFoundException(clientId);
                });
    }

    private void validateWishlistLimit(WishlistEntity wishlistEntity) {
        if (wishlistEntity.getProducts().size() >= MAX_PRODUCTS) {
            logger.error("WishlistEntity for client with ID: {} exceeds the limit of {} productyEntities", wishlistEntity.getClientId(), MAX_PRODUCTS);
            throw new WishlistLimitExceededException(wishlistEntity.getClientId());
        }
    }

    private void validateProductNotInWishlist(WishlistEntity wishlistEntity, AddProductRequestOutputDto productyEntity) {
        boolean exists = wishlistEntity.getProducts().stream()
                .anyMatch(p -> p.getId().equals(productyEntity.getId()));
        if (exists) {
            logger.error("ProductEntity with ID: {} already exists in wishlistEntity for client with ID: {}", productyEntity.getId(), wishlistEntity.getClientId());
            throw new ProductAlreadyInWishlistException(wishlistEntity.getClientId(), productyEntity.getId());
        }
    }

    private static void validateProductInWhishlist(String clientId, String productId, WishlistEntity wishlistEntity) {
        if (wishlistEntity.getProducts().stream().noneMatch(product -> product.getId().equals(productId))) {
            logger.error("ProductEntity with ID: {} not found in wishlistEntity for client with ID: {}", productId, clientId);
            throw new ProductNotFoundException(clientId, productId);
        }
    }
}
