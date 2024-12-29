package com.marvim.wishlist.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddProductRequest(
        @NotBlank String id,
        @NotBlank String name,
        String description
) {}
