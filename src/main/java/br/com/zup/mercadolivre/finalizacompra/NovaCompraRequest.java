package br.com.zup.mercadolivre.finalizacompra;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.cadastraproduto.Produto;

public class NovaCompraRequest {

	@Positive
	private Long quantidade;
	@NotNull
	private Long idProduto;
	@NotNull
	private GatewayPagamento gateway;
	
	public NovaCompraRequest(@Positive Long quantidade,
			@NotNull Long idProduto,GatewayPagamento gateway) {
		super();
		this.quantidade = quantidade;
		this.idProduto = idProduto;
		this.gateway = gateway;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public Compra toModel(Produto produtoSelecionado, Usuario comprador) {
		return new Compra(produtoSelecionado, quantidade, comprador, this.gateway);
	}
}
