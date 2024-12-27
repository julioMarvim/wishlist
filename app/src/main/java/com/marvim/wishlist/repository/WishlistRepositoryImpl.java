package com.marvim.wishlist.repository;

import com.marvim.wishlist.output.dto.response.WishlistResponseOutputDto;
import com.marvim.wishlist.repository.entity.WishlistEntity;
import com.marvim.wishlist.repository.mapper.AddProductToEntityMapper;
import com.marvim.wishlist.input.exception.ProductAlreadyInWishlistException;
import com.marvim.wishlist.input.exception.ProductNotFoundException;
import com.marvim.wishlist.input.exception.WishlistLimitExceededException;
import com.marvim.wishlist.repository.entity.WishlistFactory;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;
import com.marvim.wishlist.repository.mapper.WishlistOutputToEntityMapper;
import com.marvim.wishlist.repository.mapper.WishlistToOutputMapper;
import com.marvim.wishlist.repository.mongo.SpringDataWishlistRepository;
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
        logger.info("Starting database operation to save wishlist for client with ID: {}", clientId);
        WishlistEntity wishlistEntity = repository.findByClientId(clientId)
                .orElseGet(() -> WishlistFactory.createNew(clientId));

        wishlistValidations(addProductRequestOutputDto, wishlistEntity);
        wishlistEntity.addProduct(AddProductToEntityMapper.toEntity(addProductRequestOutputDto));
        repository.save(wishlistEntity);
        logger.info("Wishlist with ID: {} successfully updated in database for customer with ID: {}", wishlistEntity.getId(), wishlistEntity.getClientId());
    }

    private void wishlistValidations(AddProductRequestOutputDto addProductRequestOutputDto, WishlistEntity wishlistEntity) {
        validateWishlistLimit(wishlistEntity);
        validateProductAlreadyExistsInWishlist(wishlistEntity, addProductRequestOutputDto);
    }

    @Override
    public void remove(String clientId, String productId) {
        logger.info("Starting database operation to remove product with ID: {} in wishlist from client with ID: {}", productId, clientId);
        WishlistResponseOutputDto wishlistDto = findOrCreate(clientId);
        WishlistEntity wishlistEntity = WishlistOutputToEntityMapper.toOutputDto(wishlistDto);
        wishlistEntity.removeProduct(productId);
        repository.save(wishlistEntity);
        logger.info("Success in removing product with ID: {} from wishlist with ID: {} from customer with ID: {}", productId, wishlistEntity.getId(), wishlistEntity.getClientId());
    }

    public WishlistResponseOutputDto findOrCreate(String clientId) {
        return repository.findByClientId(clientId)
                .map(WishlistToOutputMapper::toOutputDto)
                .orElseGet(() -> {
                    logger.info("No wishlist found for client ID: {}. Creating a new wishlist.", clientId);
                    WishlistEntity newWishlistEntity = WishlistFactory.createNew(clientId);
                    repository.save(newWishlistEntity);
                    return WishlistToOutputMapper.toOutputDto(newWishlistEntity);
                });
    }

    @Override
    public void checkProductInWishlist(String clientId, String productId) {
        logger.info("Starting database operation to check if product with ID: {} exists in wishlist for client with ID: {}", productId, clientId);
        WishlistResponseOutputDto wishlistDto = this.findOrCreate(clientId);
        validateProductInWhishlist(clientId, productId, wishlistDto);
    }

    private void validateWishlistLimit(WishlistEntity wishlistEntity) {
        if (wishlistEntity.getProducts().size() >= MAX_PRODUCTS) {
            logger.error("Wishlist for client with ID: {} exceeds the limit of {} products", wishlistEntity.getClientId(), MAX_PRODUCTS);
            throw new WishlistLimitExceededException(wishlistEntity.getClientId());
        }
    }

    private void validateProductAlreadyExistsInWishlist(WishlistEntity wishlistEntity, AddProductRequestOutputDto productyEntity) {
        boolean exists = wishlistEntity.getProducts().stream()
                .anyMatch(p -> p.getId().equals(productyEntity.getId()));
        if (exists) {
            logger.error("ProductEntity with ID: {} already exists in wishlist for client with ID: {}", productyEntity.getId(), wishlistEntity.getClientId());
            throw new ProductAlreadyInWishlistException(wishlistEntity.getClientId(), productyEntity.getId());
        }
    }

    private static void validateProductInWhishlist(String clientId, String productId, WishlistResponseOutputDto wishlistDto) {
        if (wishlistDto.getProducts().stream().noneMatch(product -> product.getId().equals(productId))) {
            logger.error("ProductEntity with ID: {} not found in wishlist for client with ID: {}", productId, clientId);
            throw new ProductNotFoundException(clientId, productId);
        }
    }
}
