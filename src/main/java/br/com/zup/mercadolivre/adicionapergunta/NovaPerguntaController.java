package br.com.zup.mercadolivre.adicionapergunta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.cadastraproduto.Produto;
import br.com.zup.mercadolivre.compartilhado.UsuarioLogado;
import br.com.zup.mercadolivre.outrossistemas.GerenciadorEmail;

@RestController
public class NovaPerguntaController {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private GerenciadorEmail email;	

	@PostMapping(value = "/produtos/{id}/perguntas")
	@Transactional
	public String cria(@RequestBody @Valid NovaPerguntaRequest request, @PathVariable("id") Long id, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
		Produto produto = manager.find(Produto.class,id);
		
		Usuario interessado = usuarioLogado.get();
		
		Pergunta novaPergunta = request.toModel(interessado,produto);
		manager.persist(novaPergunta);
		
		email.novaPergunta(novaPergunta);
		
		return novaPergunta.toString();
	}

}
