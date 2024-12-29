package integration.stepdefinitions.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public abstract class BaseSteps {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    public ResponseEntity<String> verificarSeProdutoEstaNaWishlist(String clientId, String productId) {
        return testRestTemplate.getForEntity(
                "/api/v1/wishlist/{clientId}/{productId}/exists",
                String.class,
                clientId,
                productId
        );
    }

    public ResponseEntity<String> requisicaoParaRemoverProdutoDaWishlist(String productId, String clientId) {
        return testRestTemplate.exchange(
                "/api/v1/wishlist/{clientId}/{productId}",
                HttpMethod.DELETE,
                null,
                String.class,
                clientId,
                productId
        );
    }
}

