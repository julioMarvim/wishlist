package com.marvim.wishlist.usecases.mapper;

import com.marvim.wishlist.input.dto.request.AddProductRequestInputDto;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;

public class AddProductMapper {

    public static AddProductRequestOutputDto toOutputDto(AddProductRequestInputDto request) {
        return AddProductRequestOutputDto.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
