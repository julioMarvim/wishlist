package com.marvim.wishlist.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseInputDto {
    private String id;
    private String clientId;
    private List<ProductResponseInputDto> products;
}