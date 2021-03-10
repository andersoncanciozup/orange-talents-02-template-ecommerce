package br.com.zup.mercadolivre.processapagamento;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.mercadolivre.finalizacompra.Compra;
import br.com.zup.mercadolivre.outrossistemas.GerenciadorEmail;

@Service
public class EventosNovaCompra {

	@Autowired
	private Set<EventoCompraSucesso> eventosCompraSucesso;
	@Autowired
	private GerenciadorEmail email;

	public void executa(Compra compra) {

		if (compra.processadaComSucesso()) {
			email.processadaComSucesso(compra);
			eventosCompraSucesso.forEach(evento -> evento.executa(compra));
		} else {
			email.processamentoFalhou(compra);
		}
	}
}
