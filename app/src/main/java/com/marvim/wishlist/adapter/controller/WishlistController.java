package com.marvim.wishlist.adapter.controller;

import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.adapter.controller.dto.response.WishlistResponse;
import com.marvim.wishlist.application.mapper.ProductMapper;
import com.marvim.wishlist.application.mapper.WishlistMapper;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.entity.Wishlist;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import com.marvim.wishlist.domain.ports.input.CheckProductInWishlistUseCase;
import com.marvim.wishlist.domain.ports.input.GetWishlistUseCase;
import com.marvim.wishlist.domain.ports.input.RemoveProductFromWishlistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final AddProductToWishlistUseCase addProductToWishlistUseCase;
    private final RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;
    private final GetWishlistUseCase getWishlistUseCase;
    private final CheckProductInWishlistUseCase checkProductInWishlistUseCase;

    @PostMapping("/{clientId}")
    public ResponseEntity<Void> add(@PathVariable("clientId") String clientId,
                                    @RequestBody AddProductRequest request) {
        Product product = ProductMapper.toDomain(request);
        addProductToWishlistUseCase.execute(clientId, product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{clientId}/{productId}")
    public ResponseEntity<Void> remove(@PathVariable("clientId") String clientId,
                                       @PathVariable("productId") String productId) {
        removeProductFromWishlistUseCase.execute(clientId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<WishlistResponse> getWishlist(@PathVariable("clientId") String clientId) {
        Wishlist wishlist = getWishlistUseCase.execute(clientId);
        WishlistResponse response = WishlistMapper.toResponse(wishlist);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{clientId}/{productId}/exists")
    public ResponseEntity<Boolean> checkProductInWishlist(@PathVariable("clientId") String clientId,
                                                          @PathVariable("productId") String productId) {
        boolean exists = checkProductInWishlistUseCase.execute(clientId, productId);
        return ResponseEntity.ok(exists);
    }

}