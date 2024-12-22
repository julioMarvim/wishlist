package com.marvim.wishlist.adapter.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponse {
    private String clientId;
    private List<ProductResponse> products;
}