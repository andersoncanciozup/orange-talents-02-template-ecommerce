package br.com.zup.mercadolivre.processapagamento;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.zup.mercadolivre.finalizacompra.Compra;

@Entity
public class Transacao {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private StatusTransacao status;
	private String idTransacaoGateway;
	private LocalDateTime instante;
	@ManyToOne
	private Compra compra;
	
	@Deprecated
	public Transacao() {
		
	}

	public Transacao(StatusTransacao status, String idTransacaoGateway, Compra compra) {
		super();
		this.status = status;
		this.idTransacaoGateway = idTransacaoGateway;
		this.instante = LocalDateTime.now();
		this.compra = compra;
	}
	
	public boolean concluidaComSucesso() {
		return this.status.equals(StatusTransacao.sucesso);
	}

}
