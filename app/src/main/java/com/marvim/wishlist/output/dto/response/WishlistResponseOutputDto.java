package com.marvim.wishlist.output.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseOutputDto {
    private String id;
    private String clientId;
    private List<ProductResponseOutputDto> products;
}