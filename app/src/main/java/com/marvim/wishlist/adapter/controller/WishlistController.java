package com.marvim.wishlist.adapter.controller;

import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.application.mapper.ProductMapper;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final AddProductToWishlistUseCase addProductToWishlistUseCase;

    @PostMapping("/{clientId}")
    public ResponseEntity<Void> add( @PathVariable("clientId") String clientId, @RequestBody AddProductRequest request) {
        Product product = ProductMapper.toDomain(request);
        addProductToWishlistUseCase.execute(clientId, product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}