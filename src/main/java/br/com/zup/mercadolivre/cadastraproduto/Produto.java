package br.com.zup.mercadolivre.cadastraproduto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import br.com.zup.mercadolivre.adicionaimagem.ImagemProduto;
import br.com.zup.mercadolivre.adicionaopiniao.Opiniao;
import br.com.zup.mercadolivre.adicionapergunta.Pergunta;
import br.com.zup.mercadolivre.cadastracategoria.Categoria;
import br.com.zup.mercadolivre.cadastraousuario.Usuario;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank String nome;
	private @Positive int quantidade;
	private @NotBlank @Length(max = 1000) String descricao;
	private @NotNull @Positive BigDecimal valor;
	@NotNull
	@Valid
	@ManyToOne
	private Categoria categoria;
	@NotNull
	@Valid
	@ManyToOne
	private Usuario usuario;
	@OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
	private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();
	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<ImagemProduto> imagens = new HashSet<>();
	@OneToMany(mappedBy = "produto")
	@OrderBy("titulo asc")
	private SortedSet<Pergunta> perguntas = new TreeSet<>();
	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<Opiniao> opinioes = new HashSet<>();

	@Deprecated
	public Produto() {

	}

	public Produto(@NotBlank String nome, @Positive int quantidade,
			@NotBlank @Length(max = 1000) String descricao,
			@NotNull @Positive BigDecimal valor,
			@NotNull @Valid Categoria categoria, @NotNull @Valid Usuario usuario,
			@Size(min = 3) @Valid Collection<NovaCaracteristicaRequest> caracteristicas) {

		this.nome = nome;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.valor = valor;
		this.categoria = categoria;
		this.usuario = usuario;
		this.caracteristicas.addAll(caracteristicas.stream()
				.map(caracteristica -> caracteristica.toModel(this))
				.collect(Collectors.toSet()));

		Assert.isTrue(this.caracteristicas.size() >= 3,
				"O produto precisa ter no mínimo 3 características");

	}

	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", quantidade="
				+ quantidade + ", descricao=" + descricao + ", valor=" + valor
				+ ", categoria=" + categoria + ", usuario=" + usuario
				+ ", caracteristicas=" + caracteristicas + ", imagens="
				+ imagens + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public void associaImagens(Set<String> links) {
		Set<ImagemProduto> imagens = links.stream()
				.map(link -> new ImagemProduto(this, link))
				.collect(Collectors.toSet());

		this.imagens.addAll(imagens);
	}

	public boolean pertenceAoUsuario(Usuario possivelusuario) {
		return this.usuario.equals(possivelusuario);
	}

	public Usuario getusuario() {
		return this.usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public <T> Set<T> mapeiaCaracteristicas(
			Function<CaracteristicaProduto, T> funcaoMapeadora) {
		return this.caracteristicas.stream().map(funcaoMapeadora)
				.collect(Collectors.toSet());
	}

	public <T> Set<T> mapeiaImagens(Function<ImagemProduto, T> funcaoMapeadora) {
		return this.imagens.stream().map(funcaoMapeadora)
				.collect(Collectors.toSet());
	}
	
	public <T extends Comparable<T>> SortedSet<T> mapeiaPerguntas(Function<Pergunta, T> funcaoMapeadora) {
		return this.perguntas.stream().map(funcaoMapeadora)
				.collect(Collectors.toCollection(TreeSet :: new));
	}

	public Opinioes getOpinioes() {
		return new Opinioes(this.opinioes);
	}

	public boolean verificaQtdEstoque(@Positive Long quantidade) {
		Assert.isTrue(quantidade > 0, "A quantidade deve ser maior que zero para abater o estoque "+ quantidade);
		
		if(quantidade <= this.quantidade) {
			this.quantidade-=quantidade;
			return true;
			
		}
		
		return false;
	}



}
