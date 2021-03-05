package br.com.zup.mercadolivre.adicionaimagem;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.cadastraproduto.Produto;
import br.com.zup.mercadolivre.compartilhado.UsuarioLogado;

@RestController
public class ImagemController {
	
	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Uploader uploader;

	@PostMapping(value = "/produtos/{id}/imagens")
	@Transactional
	public String adicionaImagens(@PathVariable("id") Long id,@Valid NovasImagensRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
		
		Usuario usuario = usuarioLogado.get();
		Produto produto = manager.find(Produto.class, id);
		
		if(!produto.pertenceAoUsuario(usuario)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		Set<String> links = uploader.envia(request.getImagens());
		produto.associaImagens(links);
		
		manager.merge(produto);
		
		return produto.toString();
		
	}
}