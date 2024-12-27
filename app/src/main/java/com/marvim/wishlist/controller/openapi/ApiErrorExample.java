package com.marvim.wishlist.controller.openapi;

public class ApiErrorExample {
    public static final String ERROR_RESPONSE_EXAMPLE = """
                {
                  "data": {
                    "code": "ERROR_CODE",
                    "errors": [
                      {
                        "field": null,
                        "message": "error code message"
                      }
                    ]
                  }
                }
            """;

    public static final String VALIDATION_FAILED_EXAMPLE = """
                {
                           "data": {
                             "code": "VALIDATION_FAILED",
                             "errors": [
                               {
                                 "field": "id",
                                 "message": "must not be blan."
                               },
                               {
                                 "field": "name",
                                 "message": "must not be blan"
                               }
                             ]
                           }
                         }
            """;

    public static final String PRODUCT_ALREADY_IN_WISHLIST_EXAMPLE = """
            {
            	"data": {
            		"code": "PRODUCT_ALREADY_IN_WISHLIST",
            		"errors": [
            			{
            				"message": "Product with ID 4 is already in the customer id 1 wishlist."
            			}
            		]
            	}
            }
            """;

    public static final String LIMIT_EXCEEDED_EXAMPLE = """
            {
            	"data": {
            		"code": "LIMIT_EXCEEDED",
            		"errors": [
            			{
            				"message": "Customer ID 2 has exceeded the maximum number of products in the wishlist."
            			}
            		]
            	}
            }
            """;

    public static final String PRODUCT_NOT_FOUND_ERROR = """
            {
             	"data": {
             		"code": "PRODUCT_NOT_FOUND_ERROR",
             		"errors": [
             			{
             				"message": "Product 4 not found in customer wishlist with id: 1"
             			}
             		]
             	}
             }
            """;
}
