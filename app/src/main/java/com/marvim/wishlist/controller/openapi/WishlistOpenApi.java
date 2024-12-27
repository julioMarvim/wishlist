package com.marvim.wishlist.controller.openapi;

import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ApiResponseDto;
import com.marvim.wishlist.controller.dto.response.ErrorResponseDto;
import com.marvim.wishlist.controller.dto.response.WishlistResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.marvim.wishlist.controller.openapi.ApiErrorExample.*;

public interface WishlistOpenApi {

    @Operation(
            tags = "Api - WishList v1.0.0",
            summary = "Add a product to the wishlist",
            description = "This endpoint allows the client to add a product to their wishlist. It accepts a clientId and product information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product insertion completed successfully.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An internal error occurred while running the microservice.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = ERROR_RESPONSE_EXAMPLE)})
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The request is invalid due to bean validation errors.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = VALIDATION_FAILED_EXAMPLE)})
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product already exists in the customer wishlist.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = PRODUCT_ALREADY_IN_WISHLIST_EXAMPLE)})
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The customer has exceeded the maximum number of products in the wishlist.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = LIMIT_EXCEEDED_EXAMPLE)})
            )
    })
    @PostMapping("/{clientId}")
    ResponseEntity<Void> add(
            @Parameter(description = "Client ID", required = true) @PathVariable("clientId") String clientId,
            @Parameter(description = "Request body with product data.", required = true) @Valid @RequestBody AddProductRequest request);


    @Operation(
            tags = "Api - WishList v1.0.0",
            summary = "Remove a product from the wishlist",
            description = "This endpoint allows the client to remove a product from their wishlist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Product removal completed successfully.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found for cilent wishlist.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = PRODUCT_NOT_FOUND_ERROR)})
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An internal error occurred while running the microservice.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = ERROR_RESPONSE_EXAMPLE)})
            )
    })
    @DeleteMapping("/{clientId}/{productId}")
    ResponseEntity<ApiResponseDto<Void>> remove(
            @Parameter(description = "Client ID", required = true) @PathVariable("clientId") String clientId,
            @Parameter(description = "Product ID", required = true) @PathVariable("productId") String productId);

    @Operation(
            tags = "Api - WishList v1.0.0",
            summary = "Get the wishlist of a client",
            description = "This endpoint allows the client to retrieve their wishlist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Wishlist retrieved successfully.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An internal error occurred while running the microservice.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = ERROR_RESPONSE_EXAMPLE)})
            )
    })
    @GetMapping("/{clientId}")
    ResponseEntity<ApiResponseDto<WishlistResponseDto>> getWishlist(
            @Parameter(description = "Client ID", required = true) @PathVariable("clientId") String clientId);

    @Operation(
            tags = "Api - WishList v1.0.0",
            summary = "Check if product exists in the wishlist",
            description = "This endpoint allows the client to check if a product is already in their wishlist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product exists in the wishlist.",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found for cilent wishlist.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = PRODUCT_NOT_FOUND_ERROR)})
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An internal error occurred while running the microservice.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponseDto.class)),
                            examples = {@ExampleObject(value = ERROR_RESPONSE_EXAMPLE)})
            )
    })
    @GetMapping("/{clientId}/{productId}/exists")
    ResponseEntity<ApiResponseDto<Void>> checkProductInWishlist(
            @Parameter(description = "Client ID", required = true) @PathVariable("clientId") String clientId,
            @Parameter(description = "Product ID", required = true) @PathVariable("productId") String productId);
}

