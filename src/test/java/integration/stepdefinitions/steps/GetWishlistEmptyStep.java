package integration.stepdefinitions.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.controller.dto.response.ApiResponse;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.response.WishlistResponseOutput;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GetWishlistEmptyStep {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WishlistRepository wishlistRepository;

    private String clientId;
    private ResponseEntity<String> response;

    @Dado("que não existe uma wishlist cadastrada no sistema para o cliente com clientId {string}")
    public void queNaoExisteUmaWishlistCadastradaNoSistemaParaOClienteComClientId(String clientId) {
        this.clientId = clientId;
        WishlistResponseOutput wishlistDto = wishlistRepository.findOrCreate(clientId);
        assertThat(wishlistDto.products().isEmpty()).isTrue();
    }

    @Quando("eu faço uma requisição GET para obter a wishlist do cliente com clientId {string}")
    public void euFacoUmaRequisicaoGETParaObterAWishlistDoClienteComClientId(String clientId) {
        response = testRestTemplate.getForEntity("/api/v1/wishlist/{clientId}", String.class, clientId);
    }

@Entao("a resposta quando o usuario não tem produtos na wishlist deve ter o status code OK {int}")
    public void aRespostaDeveTerOStatusCode200(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @Entao("a resposta deve conter uma wishlist com a lista de produtos vazia")
    public void aRespostaDeveConterUmaWishlistComaListaDeProdutosVazia() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<WishlistResponseOutput> apiResponse = objectMapper.readValue(response.getBody(),
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, WishlistResponseOutput.class));

        WishlistResponseOutput wishlistDto = apiResponse.data();
        assertThat(wishlistDto.clientId()).isEqualTo(clientId);
        assertThat(wishlistDto.products()).isEmpty();
    }

}
