package com.marvim.wishlist.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WishlistResponseInput {
    private String id;
    private String clientId;
    private List<ProductResponseInput> products;
}