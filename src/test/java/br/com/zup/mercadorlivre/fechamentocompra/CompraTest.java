package br.com.zup.mercadorlivre.fechamentocompra;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.zup.mercadolivre.cadastracategoria.Categoria;
import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.cadastraproduto.NovaCaracteristicaRequest;
import br.com.zup.mercadolivre.cadastraproduto.Produto;
import br.com.zup.mercadolivre.finalizacompra.Compra;
import br.com.zup.mercadolivre.finalizacompra.GatewayPagamento;
import br.com.zup.mercadolivre.processapagamento.RetornoGatewayPagamento;
import br.com.zup.mercadolivre.processapagamento.StatusTransacao;
import br.com.zup.mercadolivre.processapagamento.Transacao;

public class CompraTest {

	/*
	 * 1) aceitar uma transacao com sucesso
	 * 2) não pode aceitar mais de uma transacao com sucesso	 * 
	 * 3) não pode aceitar transacoes com erro quando ja foi concluido com sucesso
	 * 4) pode aceitar uma transacao com erro
	 * 5) pode aceitar mais de uma transacao com erro
	 * 6) pode aceitar uma transacao com erro e uma transacao com sucesso
	 * 7) pode aceitar mais de uma transacao com erro e uma transacao com sucesso   
	 */
	
	@Test
	@DisplayName("aceitar uma transacao com sucesso")
	void deveriaAdicionarUmaTransacaoComSucesso() {
		Compra novaCompra = novaCompra();
		RetornoGatewayPagamento retornoGatewayPagamento = (compra) -> {
			return new Transacao(StatusTransacao.sucesso, "1", compra);
		};
		
		novaCompra.adicionaTransacao(retornoGatewayPagamento);
	}
	
	@Test
	@DisplayName("não pode aceitar mais de uma transacao com sucesso")
	void naoDeveriaAdicionarMaisDeUmaTransacaoComSucesso() {
		Compra novaCompra = novaCompra();
		RetornoGatewayPagamento retornoGatewayPagamento = (compra) -> {
			return new Transacao(StatusTransacao.sucesso, "1", compra);
		};
		
		novaCompra.adicionaTransacao(retornoGatewayPagamento);
		
		RetornoGatewayPagamento retornoGatewayPagamento2 = (compra) -> {
			return new Transacao(StatusTransacao.sucesso, "2", compra);
		};
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			novaCompra.adicionaTransacao(retornoGatewayPagamento2);
		});
	}
	
	@Test
	@DisplayName("não pode aceitar transacao com erro quando ja foi concluido com sucesso")
	void naoPodeAceitarTransacaoComErroQuandoJaFoiConcluidaComSucesso() {
		Compra novaCompra = novaCompra();
		RetornoGatewayPagamento retornoGatewayPagamento = (compra) -> {
			return new Transacao(StatusTransacao.sucesso, "1", compra);
		};
		
		novaCompra.adicionaTransacao(retornoGatewayPagamento);
		
		RetornoGatewayPagamento retornoGatewayPagamento2 = (compra) -> {
			return new Transacao(StatusTransacao.erro, "2", compra);
		};
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			novaCompra.adicionaTransacao(retornoGatewayPagamento2);
		});
	}
	
	private Compra novaCompra() {
		Categoria categoria = new Categoria("teste");
		Usuario dono = new Usuario("email@email.com", "123456");
		Collection<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();
		caracteristicas.add(new NovaCaracteristicaRequest("nome", "descricao"));
		caracteristicas	
				.add(new NovaCaracteristicaRequest("nome1", "descricao"));
		caracteristicas
				.add(new NovaCaracteristicaRequest("nome2", "descricao"));

		Produto produtoASerComprado = new Produto("teste", 100, "descricao",
				BigDecimal.TEN, categoria, dono, caracteristicas);

		Usuario comprador = new Usuario("comprador@email.com", "1234567");

		GatewayPagamento gatewayPagamento = GatewayPagamento.pagseguro;
		
		return new Compra(produtoASerComprado, 20L, comprador, gatewayPagamento);
	}
}
