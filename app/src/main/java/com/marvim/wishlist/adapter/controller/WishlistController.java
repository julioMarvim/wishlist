package com.marvim.wishlist.adapter.controller;

import com.marvim.wishlist.adapter.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.application.mapper.ProductMapper;
import com.marvim.wishlist.domain.entity.Product;
import com.marvim.wishlist.domain.ports.input.AddProductToWishlistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final AddProductToWishlistUseCase addProductToWishlistUseCase;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody AddProductRequest request) {
        Product product = ProductMapper.toDomain(request);
        addProductToWishlistUseCase.execute(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}