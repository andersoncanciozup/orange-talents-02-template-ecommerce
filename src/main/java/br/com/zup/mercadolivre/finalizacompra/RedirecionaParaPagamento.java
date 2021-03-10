package br.com.zup.mercadolivre.finalizacompra;

import org.springframework.web.util.UriComponentsBuilder;

public class RedirecionaParaPagamento {
	
	private Compra compra;
	private UriComponentsBuilder uriComponentsBuilder;
	
	public RedirecionaParaPagamento(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
		this.compra = compra;
		this.uriComponentsBuilder = uriComponentsBuilder;
	}

	public String urlRetorno() {

		if (compra.getGatewayPagamento().equals(GatewayPagamento.pagseguro)) {

			String urlRetornoPagseguro = uriComponentsBuilder.path("/retorno-pagseguro/{id}")
					.buildAndExpand(compra.getId()).toString();

			return "pagseguro.com/" + compra.getId() + "?redirectUrl=" + urlRetornoPagseguro;

		}

		String urlRetornoPaypal = uriComponentsBuilder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId())
				.toString();

		return "paypal.com/" + compra.getId() + "?redirectUrl=" + urlRetornoPaypal;
	}

}
