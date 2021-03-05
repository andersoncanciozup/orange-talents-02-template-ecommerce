package br.com.zup.mercadolivre.adicionapergunta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GerenciadorEmail {
	
	@Autowired
	private EmailFake emailFake;

	public void novaPergunta(@NotNull @Valid Pergunta pergunta) {
		emailFake.enviar("<html>...</html>","Nova pergunta...",pergunta.getInteressada().getEmail(),"novapergunta@nossomercadolivre.com",pergunta.getDonoProduto().getEmail());
	}

//	public void novaCompra(Compra novaCompra) {
//		mailer.send("nova compra..." + novaCompra, "Você tem uma nova compra",
//				novaCompra.getComprador().getEmail(),
//				"compras@nossomercadolivre.com",
//				novaCompra.getDonoProduto().getEmail());
//	}

	
}
