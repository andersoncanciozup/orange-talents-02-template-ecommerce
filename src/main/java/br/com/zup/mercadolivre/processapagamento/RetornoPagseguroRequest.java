package br.com.zup.mercadolivre.processapagamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.mercadolivre.finalizacompra.Compra;

public class RetornoPagseguroRequest implements RetornoGatewayPagamento {
	@NotBlank
	private String idTransacao;
	@NotNull
	private StatusRetornoPagseguro status;
	
	public RetornoPagseguroRequest(@NotBlank String idTransacao,
			StatusRetornoPagseguro status) {
		super();
		this.idTransacao = idTransacao;
		this.status = status;
	}

	public Transacao toTransacao(Compra compra) {
		return new Transacao(status.normaliza(),idTransacao,compra);
	} 
}
