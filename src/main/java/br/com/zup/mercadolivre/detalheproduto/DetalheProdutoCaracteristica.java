package br.com.zup.mercadolivre.detalheproduto;

import br.com.zup.mercadolivre.cadastraproduto.CaracteristicaProduto;

public class DetalheProdutoCaracteristica {

	private String nome;
	private String descricao;

	public DetalheProdutoCaracteristica(CaracteristicaProduto caracteristica) {
		this.nome = caracteristica.getNome();
		this.descricao = caracteristica.getDescricao();
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
