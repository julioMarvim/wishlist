package com.marvim.wishlist.controller.dto.response;

import java.util.List;

public record WishlistResponseDto(String id, String clientId, List<ProductResponseDto> products) {}
