package com.marvim.wishlist.output.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductRequestOutputDto {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;
}