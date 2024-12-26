package com.marvim.wishlist.controller;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ApiResponse;
import com.marvim.wishlist.controller.dto.response.WishlistResponse;
import com.marvim.wishlist.controller.mapper.AddProductToInputMapper;
import com.marvim.wishlist.controller.mapper.WishlistToResponseMapper;
import com.marvim.wishlist.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.input.GetWishlistUseCase;
import com.marvim.wishlist.input.RemoveProductFromWishlistUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    private final AddProductToWishlistUseCase addProductToWishlistUseCase;
    private final RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;
    private final GetWishlistUseCase getWishlistUseCase;
    private final CheckProductInWishlistUseCase checkProductInWishlistUseCase;

    @PostMapping("/{clientId}")
    public ResponseEntity<Void> add(@PathVariable("clientId") String clientId,
                                    @Valid @RequestBody AddProductRequest request) {
        logger.info("Received request to add product with ID: {} to wishlist for client with ID: {}", request.getId(), clientId);
        addProductToWishlistUseCase.execute(clientId, AddProductToInputMapper.toInput(request));
        logger.info("Success in adding the product with ID: {} to the wish list for the customer with ID: {}", request.getId(), clientId);
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
    public ResponseEntity<ApiResponse<WishlistResponse>> getWishlist(@PathVariable("clientId") String clientId) {
        logger.info("Received request to fetch wishlistEntity for client with ID: {}", clientId);
        WishlistResponse wishlistResponse = WishlistToResponseMapper.toResponse(getWishlistUseCase.execute(clientId));
        logger.info("WishlistEntity for client with ID: {} retrieved successfully", clientId);
        return ResponseEntity.ok(new ApiResponse<>(wishlistResponse));
    }

    @GetMapping("/{clientId}/{productId}/exists")
    public ResponseEntity<ApiResponse<Void>> checkProductInWishlist(@PathVariable("clientId") String clientId,
                                                                    @PathVariable("productId") String productId) {
        logger.info("Received request to check if product with ID: {} exists in client {}'s wishlist", productId, clientId);
        checkProductInWishlistUseCase.execute(clientId, productId);
        logger.info("Success in checking if the product with ID; {} exists in to the wish list for the customer with ID: {}", productId, clientId);
        return ResponseEntity.ok().build();
    }
}
