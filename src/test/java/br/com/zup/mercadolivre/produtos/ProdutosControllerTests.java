package br.com.zup.mercadolivre.produtos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.mercadolivre.cadastraproduto.NovaCaracteristicaRequest;
import br.com.zup.mercadolivre.cadastraproduto.NovoProdutoRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutosControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void deveriaCriarProdudo() throws JsonProcessingException, Exception {
		
		List<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();
		caracteristicas.add(new NovaCaracteristicaRequest("Alteura", "alto"));
		caracteristicas.add(new NovaCaracteristicaRequest("largura", "largo"));
		caracteristicas.add(new NovaCaracteristicaRequest("comprimento", "comprido"));
		
		
		mockMvc.perform(post("/produtos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new NovoProdutoRequest("A21s", 10, "um bom produto", new BigDecimal(1200), 1L, caracteristicas))))
		.andExpect(status().isOk());
		
	}
}
