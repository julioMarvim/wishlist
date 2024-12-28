package integration.stepdefinitions.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.controller.dto.request.AddProductRequest;
import com.marvim.wishlist.controller.dto.response.ApiResponse;
import com.marvim.wishlist.controller.dto.response.ErrorResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AddProductMethodArgumentNotValidStep {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ResponseEntity<String> response;

    @Quando("eu faço uma requisição POST para adicionar um produto a wishlist com os seguintes dados inválidos:")
    public void euFacoUmaRequisicaoPOSTParaAdicionarUmProdutoAWishlistComOsSeguintesDadosInvalidos(DataTable dataTable) throws JsonProcessingException {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> productData = rows.get(0);

        AddProductRequest addProductRequest = AddProductRequest.builder()
                .id(productData.get("id"))
                .name(productData.get("name"))
                .description(productData.get("description"))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(
                new ObjectMapper().writeValueAsString(addProductRequest), headers);

        response = testRestTemplate.postForEntity(
                "/api/v1/wishlist/{clientId}",
                requestEntity,
                String.class,
                "test-client"
        );
    }

    @Entao("a resposta deve ter o status code BAD_REQUEST {int}")
    public void aRespostaDeveTerOStatusCodeBAD_REQUEST400(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @Entao("a resposta deve conter um VALIDATION_FAILED")
    public void aRespostaDeveConterUmVALIDATION_FAILED() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<ErrorResponse> apiResponse = objectMapper.readValue(response.getBody(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, ErrorResponse.class));

        ErrorResponse errorResponse = apiResponse.data();
        assertThat(errorResponse.getCode()).isEqualTo("VALIDATION_FAILED");
        assertThat(errorResponse.getErrors()).hasSize(1);
        assertThat(errorResponse.getErrors().get(0).getMessage()).isNotEmpty();
    }
}
