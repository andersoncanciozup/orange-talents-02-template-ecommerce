package br.com.zup.mercadolivre.detalheproduto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.mercadolivre.cadastraproduto.Produto;

@RestController
public class DetalheProdutoController {

	@PersistenceContext
	private EntityManager manager;

	@GetMapping(value = "/produtos/{id}")
	public DetalheProdutoResponse consultaProdutoPorId(@PathVariable("id") Long id) {
		Produto produtoEscolhido = manager.find(Produto.class, id);
		return new DetalheProdutoResponse(produtoEscolhido);
	}
}
