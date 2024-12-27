package com.marvim.wishlist.integration.stepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;
import com.marvim.wishlist.output.dto.response.ProductResponseOutputDto;
import com.marvim.wishlist.repository.entity.ProductEntity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class WishlistStep {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WishlistRepository wishlistRepository;

    private ResponseEntity<String> response;
    private String clientId;
    private List<AddProductRequestOutputDto> products;

    @Dado("que existe uma wishlist cadastrada no sistema:")
    public void queExisteUmaWishlistCadastradaNoSistema(DataTable dataTable) throws Exception {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            this.clientId = columns.get("clientId");
            String productsJson = columns.get("products");
            List<Map<String, String>> rawProducts = new ObjectMapper().readValue(productsJson, new TypeReference<>() {});
            this.products = rawProducts.stream()
                    .map(map -> new AddProductRequestOutputDto(
                            map.get("id"),
                            map.get("name"),
                            map.get("description")
                    ))
                    .collect(Collectors.toList());
        }

        products.forEach(product -> wishlistRepository.save(clientId, product));
    }

    @Quando("eu faço uma requisição GET para obter a wishlist")
    public void euFacoUmaRequisicaoGETParaObterAWishlist() {
        response = testRestTemplate.getForEntity("/api/v1/wishlist/{clientId}", String.class, clientId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Entao("a resposta deve ter o status code {int}")
    public void aRespostaDeveTerOStatusCode(int statusCode) {
        assertThat(response.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Entao("a resposta deve conter os dados que foram cadastrados previamente")
    public void aRespostaDeveConterOsDadosQueForamCadastradosPreviamente() {
        assertThat(response.getBody()).contains(clientId);
        products.forEach(product -> {
            assertThat(response.getBody()).contains(product.getId());
            assertThat(response.getBody()).contains(product.getName());
            assertThat(response.getBody()).contains(product.getDescription());
        });
    }
}
