package br.com.zup.mercadolivre.processapagamento;

import br.com.zup.mercadolivre.finalizacompra.Compra;

public interface EventoCompraSucesso {

	void executa(Compra compra);
	
}
