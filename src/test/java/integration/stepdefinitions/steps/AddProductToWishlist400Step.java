package integration.stepdefinitions.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ApiResponse;
import com.marvim.wishlist.controller.dto.response.ErrorResponse;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AddProductToWishlist400Step {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WishlistRepository wishlistRepository;

    private String clientId;
    private AddProductRequest addProductRequest;
    private ResponseEntity<String> response;

    @Dado("que existe uma wishlist cadastrada no sistema para o clientId 8:")
    public void queExisteUmaWishlistCadastradaNoSistemaParaOClientId8(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            this.clientId = row.get("clientId");
            var product = new AddProductRequestOutput(
                    row.get("id"),
                    row.get("name"),
                    row.get("description")
            );
            wishlistRepository.save(clientId, product);
        }
    }

    @Quando("eu faço uma requisição POST para adicionar o produto a wishlist do cliente 8")
    public void euFacoUmaRequisicaoPOSTParaAdicionarOProdutoAWishlistDoCliente8() throws JsonProcessingException {
        addProductRequest = new AddProductRequest("1", "Garrafa", "Garrafa de cafe");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(
                new ObjectMapper().writeValueAsString(addProductRequest), headers);

        response = testRestTemplate.postForEntity(
                "/api/v1/wishlist/{clientId}",
                requestEntity,
                String.class,
                clientId
        );
    }

    @Entao("a resposta quando o produto for adicionado na wishlist deve ter o status code BAD_REQUEST {int}")
    public void aRespostaQuandoOProdutoForAdicionadoNaWishlistDeveTerOStatusCodeBAD_REQUEST400(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @Entao("a resposta deve conter um PRODUCT_ALREADY_IN_WISHLIST")
    public void aRespostaDeveConterUmPRODUCT_ALREADY_IN_WISHLIST() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<ErrorResponse> apiResponse = objectMapper.readValue(response.getBody(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, ErrorResponse.class));

        ErrorResponse errorResponse = apiResponse.data();
        assertThat(errorResponse.code()).isEqualTo("PRODUCT_ALREADY_IN_WISHLIST");
        assertThat(errorResponse.errors()).hasSize(1);
        assertThat(errorResponse.errors().get(0).message()).isEqualTo("Product with ID 1 is already in the customer id 8 wishlist.");
    }
}
