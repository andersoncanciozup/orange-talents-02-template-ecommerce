package br.com.zup.mercadolivre.finalizacompra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.cadastraproduto.Produto;
import br.com.zup.mercadolivre.compartilhado.UsuarioLogado;
import br.com.zup.mercadolivre.outrossistemas.GerenciadorEmail;

@RestController
public class FinalizaCompraController {

	@PersistenceContext
	EntityManager manager;
	
	@Autowired
	private GerenciadorEmail gerenciadorEmail;
	
	@PostMapping(value = "/compras")
	@Transactional
	public ResponseEntity<?> criaCompra(@RequestBody @Valid NovaCompraRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado, UriComponentsBuilder uriComponentsBuilder) throws URISyntaxException {
		
		Produto produtoSelecionado = manager.find(Produto.class, request.getIdProduto());
		
		 boolean qtdDisponivel = produtoSelecionado.verificaQtdEstoque(request.getQuantidade());
		
		 if (!qtdDisponivel) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantidade solicitada superior a quantidade em estoque");
		 }
		 
		
			Usuario comprador = usuarioLogado.get();
			
			Compra novaCompra = request.toModel(produtoSelecionado, comprador);
			
			manager.persist(novaCompra);
			gerenciadorEmail.novaCompra(novaCompra);
			
			URI urlParaPagamento = new URI(novaCompra.urlRedirecionamento(uriComponentsBuilder));
		
			return ResponseEntity.status(302).location(urlParaPagamento).build();
	}
	
}
