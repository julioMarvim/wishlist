package com.marvim.wishlist.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class AddProductRequest {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;
}
