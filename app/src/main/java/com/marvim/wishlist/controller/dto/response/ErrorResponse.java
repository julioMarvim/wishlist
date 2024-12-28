package com.marvim.wishlist.controller.dto.response;

import java.util.List;

public record ErrorResponse(String code, List<ErrorDetail> errors) {

}
