package br.com.zup.mercadolivre.processapagamento;

import br.com.zup.mercadolivre.finalizacompra.Compra;

public interface RetornoGatewayPagamento {

	Transacao toTransacao(Compra compra);

}
