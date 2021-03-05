package br.com.zup.mercadolivre.cadastraproduto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.compartilhado.UsuarioLogado;

@RestController
public class ProdutosController {

	@PersistenceContext
	private EntityManager manager;
	
	@InitBinder(value = "novoProdutoRequest")
	public void init(WebDataBinder webDataBinder) {
		//1
		webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
	}

	@PostMapping(value = "/produtos")
	@Transactional
	//1
	public String cria(@RequestBody @Valid NovoProdutoRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
		
		Usuario usuario = usuarioLogado.get();

		Produto produto = request.toModel(manager,usuario);
		
		manager.persist(produto);
		return produto.toString();
	}
	


}
