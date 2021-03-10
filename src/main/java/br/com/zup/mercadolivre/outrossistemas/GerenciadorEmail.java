package br.com.zup.mercadolivre.outrossistemas;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.mercadolivre.adicionapergunta.Pergunta;
import br.com.zup.mercadolivre.finalizacompra.Compra;

@Service
public class GerenciadorEmail {
	
	@Autowired
	private EmailFake emailFake;

	public void novaPergunta(@NotNull @Valid Pergunta pergunta) {
		emailFake.enviar("<html>...</html>","Nova pergunta...",pergunta.getInteressada().getEmail(),"novapergunta@mercadolivre.com",pergunta.getDonoProduto().getEmail());
	}

	public void novaCompra(Compra novaCompra) {
		emailFake.enviar("nova compra..." + novaCompra, "Você tem uma nova compra",
				novaCompra.getComprador().getEmail(),
				"compras@mercadolivre.com",
				novaCompra.getVendendor().getEmail());
	}
	
	public void processadaComSucesso(Compra compraProcessada) {
		emailFake.enviar("Ficamos felizes por ter comprado conosco..." + compraProcessada, "Sua compra foi processada com sucesso",
				compraProcessada.getVendendor().getEmail(),
				"compras.processdas@mercadolivre.com",
				compraProcessada.getComprador().getEmail());
	}

	public void processamentoFalhou(Compra compra) {
		emailFake.enviar("Infelimente o processameto do seu pagamento falou, tente novamente link: mercadoLivre/compras", "A forma de pagamento falou :(",
				compra.getVendendor().getEmail(),
				"compras@mercadolivre.com",
				compra.getComprador().getEmail());
	}
	
}
