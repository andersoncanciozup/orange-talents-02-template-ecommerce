package br.com.zup.mercadolivre.cadastraprodutos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.cadastraousuario.UsuarioRepository;

@RestController
public class ProdutosController {

	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@InitBinder(value = "novoProdutoRequest")
	public void init(WebDataBinder webDataBinder) {
		//1
		webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
	}

	@PostMapping(value = "/produtos")
	@Transactional
	//1
	public String cria(@RequestBody @Valid NovoProdutoRequest request) {
		
		Usuario dono = usuarioRepository.findByEmail("alberto@deveficiente.com").get();		

		Produto produto = request.toModel(manager,dono);
		
		manager.persist(produto);
		return produto.toString();
	}
	


}
