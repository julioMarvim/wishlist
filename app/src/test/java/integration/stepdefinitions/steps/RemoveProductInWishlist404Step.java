package integration.stepdefinitions.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.controller.dto.response.ApiResponseDto;
import com.marvim.wishlist.controller.dto.response.ErrorResponseDto;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutputDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoveProductInWishlist404Step extends BaseSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WishlistRepository wishlistRepository;

    private ResponseEntity<String> response;
    private String clientId;
    private List<AddProductRequestOutputDto> products;

    @Dado("que existe uma wishlist cadastrada no sistema para o clientId 6:")
    public void queExisteUmaWishlistCadastradaNoSistemaParaOClientId5(DataTable dataTable) throws Exception {
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

    @Quando("eu faço uma requisição DELETE para remover o produto de id {string} da wishlist do clientId 6")
    public void euFacoUmaRequisicaoDeleteParaRemoverOProdutoDeIdDaWishlist(String productId) {
        response = requisicaoParaRemoverProdutoDaWishlist(productId, clientId);
    }

    @Entao("a resposta quando o produto não existir na wishlist deve ter o status code NOT_FOUND {int}")
    public void aRespostaDeveTerOStatusCode200(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @Entao("a ao remover o produto a resposta deve conter o erro PRODUCT_NOT_FOUND_ERROR")
    public void aRespostaDeveConterOsDadosQueForamCadastradosPreviamente() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponseDto<ErrorResponseDto> apiResponse = objectMapper.readValue(response.getBody(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponseDto.class, ErrorResponseDto.class));

        ErrorResponseDto errorResponse = apiResponse.data();
        assertThat(errorResponse.getCode()).isEqualTo("PRODUCT_NOT_FOUND_ERROR");
        assertThat(errorResponse.getErrors()).hasSize(1);
        assertThat(errorResponse.getErrors().get(0).getMessage()).isEqualTo("Product 2 not found in customer wishlist with id: 6");
    }
}
