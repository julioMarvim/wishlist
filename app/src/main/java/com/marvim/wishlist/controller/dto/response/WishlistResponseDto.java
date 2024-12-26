package com.marvim.wishlist.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseDto {
    private String id;
    private String clientId;
    private List<ProductResponseDto> products;
}