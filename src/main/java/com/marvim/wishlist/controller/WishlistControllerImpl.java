package com.marvim.wishlist.controller;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ApiResponse;
import com.marvim.wishlist.controller.dto.response.WishlistRespons;
import com.marvim.wishlist.controller.mapper.ControllerMappers;
import com.marvim.wishlist.controller.openapi.WishlistOpenApi;
import com.marvim.wishlist.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.input.GetWishlistUseCase;
import com.marvim.wishlist.input.RemoveProductFromWishlistUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistControllerImpl implements WishlistOpenApi {

    private static final Logger logger = LoggerFactory.getLogger(WishlistControllerImpl.class);

    @Autowired
    private AddProductToWishlistUseCase addProductToWishlistUseCase;

    @Autowired
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    @Autowired
    private GetWishlistUseCase getWishlistUseCase;

    @Autowired
    private CheckProductInWishlistUseCase checkProductInWishlistUseCase;

    @Override
    @PostMapping("/{clientId}")
    public ResponseEntity<Void> add(@PathVariable("clientId") String clientId,
                                    @Valid @RequestBody AddProductRequest request) {
        logger.info("Received request to add product with ID: {} to wishlist for client with ID: {}", request.id(), clientId);
        addProductToWishlistUseCase.execute(clientId, ControllerMappers.toInput(request));
        logger.info("Success in adding the product with ID: {} to the wish list for the customer with ID: {}", request.id(), clientId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{clientId}/{productId}")
    public ResponseEntity<ApiResponse<Void>> remove(@PathVariable("clientId") String clientId,
                                                    @PathVariable("productId") String productId) {
        logger.info("Received request to remove product with ID: {} to wishlist for client with ID: {}", productId, clientId);
        removeProductFromWishlistUseCase.execute(clientId, productId);
        logger.info("Success in remove the product with ID: {} to the wish list for the customer with ID: {}", productId, clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ApiResponse<WishlistRespons>> getWishlist(@PathVariable("clientId") String clientId) {
        logger.info("Received request to fetch wishlist for client with ID: {}", clientId);
        WishlistRespons wishlistRespons = ControllerMappers.toResponse(getWishlistUseCase.execute(clientId));
        logger.info("Wishlist for client with ID: {} retrieved successfully", clientId);
        return ResponseEntity.ok(new ApiResponse<>(wishlistRespons));
    }

    @GetMapping("/{clientId}/{productId}/exists")
    public ResponseEntity<ApiResponse<Void>> checkProductInWishlist(@PathVariable("clientId") String clientId,
                                                                    @PathVariable("productId") String productId) {
        logger.info("Received request to check if product with ID: {} exists in client {}'s wishlist", productId, clientId);
        checkProductInWishlistUseCase.execute(clientId, productId);
        logger.info("Success in checking if the product with ID: {} exists in to the wishlist for the client with ID: {}", productId, clientId);
        return ResponseEntity.ok().build();
    }
}

