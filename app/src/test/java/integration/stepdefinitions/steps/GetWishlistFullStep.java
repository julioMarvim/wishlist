package integration.stepdefinitions.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvim.wishlist.output.WishlistRepository;
import com.marvim.wishlist.output.dto.request.AddProductRequestOutput;
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

public class GetWishlistFullStep {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WishlistRepository wishlistRepository;

    private ResponseEntity<String> response;
    private String clientId;
    private List<AddProductRequestOutput> products;

    @Dado("que existe uma wishlist cadastrada no sistema para o clientId 1:")
    public void queExisteUmaWishlistCadastradaNoSistema(DataTable dataTable) throws Exception {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            this.clientId = columns.get("clientId");
            String productsJson = columns.get("products");
            List<Map<String, String>> rawProducts = new ObjectMapper().readValue(productsJson, new TypeReference<>() {});
            this.products = rawProducts.stream()
                    .map(map -> new AddProductRequestOutput(
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
    }

    @Entao("a resposta quando o usuario tem produtos na wishlist deve ter o status code OK {int}")
    public void aRespostaDeveTerOStatusCode200(int statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @Entao("a resposta deve conter os dados que foram cadastrados previamente")
    public void aRespostaDeveConterOsDadosQueForamCadastradosPreviamente() {
        assertThat(response.getBody()).contains(clientId);
        products.forEach(product -> {
            assertThat(response.getBody()).contains(product.id());
            assertThat(response.getBody()).contains(product.name());
            assertThat(response.getBody()).contains(product.description());
        });
    }
}
