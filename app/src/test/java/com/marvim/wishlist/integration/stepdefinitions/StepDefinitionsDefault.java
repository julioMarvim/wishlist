package com.marvim.wishlist.integration.stepdefinitions;

import com.marvim.wishlist.WishlistApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = WishlistApplication.class)
@ActiveProfiles("test")
public class StepDefinitionsDefault {
}
