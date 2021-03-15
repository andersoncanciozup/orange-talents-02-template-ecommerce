package br.com.zup.mercadolivre.processapagamento;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import br.com.zup.mercadolivre.finalizacompra.Compra;

@Service
public class Ranking implements EventoCompraSucesso{

	@Override
	public void executa(Compra compra) {
		Assert.isTrue(compra.processadaComSucesso(),"Compra não processada com sucesso "+compra);
		
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> request = Map.of("idCompra", compra.getId(),
				"idVendendor", compra.getVendendor().getId());		

		restTemplate.postForEntity("http://localhost:8080/ranking",
				request, String.class);		
	}

}
