package com.marvim.wishlist.adapter.controller;

import com.marvim.wishlist.adapter.controller.dto.request.InsertProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody InsertProductRequest request) {
        //implementar
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}