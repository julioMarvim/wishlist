package com.marvim.wishlist.controller.dto.response;

import java.util.List;

public record WishlistRespons(String id, String clientId, List<ProductResponse> products) {}
