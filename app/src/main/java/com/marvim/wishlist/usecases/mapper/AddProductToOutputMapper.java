package com.marvim.wishlist.usecases.mapper;

import com.marvim.wishlist.input.dto.request.AddProductRequestInput;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;

public class AddProductToOutputMapper {

    public static AddProductRequestOutput toOutputDto(AddProductRequestInput request) {
        return AddProductRequestOutput.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
