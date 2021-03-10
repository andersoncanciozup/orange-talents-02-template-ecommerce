package br.com.zup.mercadolivre.finalizacompra;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.cadastraproduto.Produto;
import br.com.zup.mercadolivre.processapagamento.RetornoGatewayPagamento;
import br.com.zup.mercadolivre.processapagamento.Transacao;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@NotNull
	@Valid
	private Produto produtoSelecionado;
	@Positive
	private Long quantidade;
	@ManyToOne
	@NotNull
	@Valid
	private Usuario comprador;
	@Enumerated
	@NotNull
	private GatewayPagamento gatewayPagamento;
	@OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
	private Set<Transacao> transacoes = new HashSet<>();

	@Deprecated
	public Compra() {

	}

	public Compra(@NotNull @Valid Produto produtoSelecionado, @Positive Long quantidade,
			@NotNull @Valid Usuario comprador, @NotNull GatewayPagamento gatewayPagamento) {
		super();
		this.produtoSelecionado = produtoSelecionado;
		this.quantidade = quantidade;
		this.comprador = comprador;
		this.gatewayPagamento = gatewayPagamento;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public Usuario getVendendor() {
		return produtoSelecionado.getusuario();
	}

	public GatewayPagamento getGatewayPagamento() {
		return gatewayPagamento;
	}

	public String urlRedirecionamento(UriComponentsBuilder uriComponentsBuilder) {
		RedirecionaParaPagamento tipoPagamento = new RedirecionaParaPagamento(this, uriComponentsBuilder);
		return tipoPagamento.urlRetorno();
	}

	public void adicionaTransacao(@Valid RetornoGatewayPagamento request) {
		Transacao novaTransacao = request.toTransacao(this);

		Assert.state(!this.transacoes.contains(novaTransacao),
				"Já existe uma transacao igual sendo processada " + novaTransacao);

		Assert.state(transacoesConcluidasComSucesso().isEmpty(), "Esse compra já foi processada com sucesso");

		this.transacoes.add(novaTransacao);
	}

	private Set<Transacao> transacoesConcluidasComSucesso() {
		Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream().filter(Transacao::concluidaComSucesso)
				.collect(Collectors.toSet());

		Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1,
				"Existe mais de uma transacao concluida com sucesso nessa mesma compra " + this.id);

		return transacoesConcluidasComSucesso;
	}

	public boolean processadaComSucesso() {
		return !transacoesConcluidasComSucesso().isEmpty();
	}
	public Long getId() {
		return id;
	}
}
