package com.marvim.wishlist.repository;

import com.marvim.wishlist.exception.global.ProductAlreadyInWishlistException;
import com.marvim.wishlist.exception.global.ProductNotFoundException;
import com.marvim.wishlist.exception.global.WishlistLimitExceededException;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import com.marvim.wishlist.repository.entity.ProductEntity;
import com.marvim.wishlist.repository.entity.WishlistEntity;
import com.marvim.wishlist.repository.entity.WishlistFactory;
import com.marvim.wishlist.repository.mapper.RepositoryMappers;
import com.marvim.wishlist.repository.mongo.SpringDataWishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository {

    private static final Logger logger = LoggerFactory.getLogger(WishlistRepositoryImpl.class);

    @Autowired
    private SpringDataWishlistRepository repository;

    private static final int MAX_PRODUCTS = 20;

    @Override
    public void save(String clientId, AddProductRequestOutput addProductRequestOutput) {
        logger.info("Starting database operation to save wishlist for client with ID: {}", clientId);
        WishlistEntity wishlistEntity = repository.findByClientId(clientId)
                .orElseGet(() -> WishlistFactory.createNew(clientId));

        wishlistValidations(addProductRequestOutput, wishlistEntity);
        ProductEntity productEntity = RepositoryMappers.toProduct(addProductRequestOutput);
        wishlistEntity.products().add(productEntity);
        repository.save(wishlistEntity);
        logger.info("Wishlist with ID: {} successfully updated in database for customer with ID: {}", wishlistEntity.id(), wishlistEntity.clientId());
    }

    private void wishlistValidations(AddProductRequestOutput addProductRequestOutput, WishlistEntity wishlistEntity) {
        validateWishlistLimit(wishlistEntity);
        validateProductAlreadyExistsInWishlist(wishlistEntity, addProductRequestOutput);
    }

    @Override
    public void remove(String clientId, String productId) {
        logger.info("Starting database operation to remove product with ID: {} in wishlist from client with ID: {}", productId, clientId);
        WishlistResponseOutput wishlistDto = findOrCreate(clientId);
        WishlistEntity wishlistEntity = RepositoryMappers.toWishlist(wishlistDto);
        WishlistEntity newWishlist = wishlistEntity.removeProduct(productId);
        repository.save(newWishlist);
        logger.info("Success in removing product with ID: {} from wishlist with ID: {} from customer with ID: {}", productId, wishlistEntity.id(), wishlistEntity.clientId());
    }

    public WishlistResponseOutput findOrCreate(String clientId) {
        return repository.findByClientId(clientId)
                .map(RepositoryMappers::toOutput)
                .orElseGet(() -> {
                    logger.info("No wishlist found for client ID: {}. Creating a new wishlist.", clientId);
                    WishlistEntity newWishlistEntity = WishlistFactory.createNew(clientId);
                    WishlistEntity savedWishlistEntity = repository.save(newWishlistEntity);
                    return RepositoryMappers.toOutput(savedWishlistEntity);
                });
    }

    @Override
    public void checkProductInWishlist(String clientId, String productId) {
        logger.info("Starting database operation to check if product with ID: {} exists in wishlist for client with ID: {}", productId, clientId);
        WishlistResponseOutput wishlistDto = this.findOrCreate(clientId);
        validateProductInWhishlist(clientId, productId, wishlistDto);
    }

    private void validateWishlistLimit(WishlistEntity wishlistEntity) {
        if (wishlistEntity.products().size() >= MAX_PRODUCTS) {
            logger.error("Wishlist for client with ID: {} exceeds the limit of {} products", wishlistEntity.clientId(), MAX_PRODUCTS);
            throw new WishlistLimitExceededException(wishlistEntity.clientId());
        }
    }

    private void validateProductAlreadyExistsInWishlist(WishlistEntity wishlistEntity, AddProductRequestOutput productyEntity) {
        boolean exists = wishlistEntity.products().stream()
                .anyMatch(p -> p.id().equals(productyEntity.id()));
        if (exists) {
            logger.error("ProductEntity with ID: {} already exists in wishlist for client with ID: {}", productyEntity.id(), wishlistEntity.clientId());
            throw new ProductAlreadyInWishlistException(wishlistEntity.clientId(), productyEntity.id());
        }
    }

    private static void validateProductInWhishlist(String clientId, String productId, WishlistResponseOutput wishlistDto) {
        if (wishlistDto.products().stream().noneMatch(product -> product.id().equals(productId))) {
            logger.error("ProductEntity with ID: {} not found in wishlist for client with ID: {}", productId, clientId);
            throw new ProductNotFoundException(clientId, productId);
        }
    }
}
