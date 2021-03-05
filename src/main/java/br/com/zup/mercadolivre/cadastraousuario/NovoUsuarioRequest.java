package br.com.zup.mercadolivre.cadastraousuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.zup.mercadolivre.compartilhado.ValorUnico;

public class NovoUsuarioRequest {

	@Email
	@NotBlank
	@ValorUnico(domainClass = Usuario.class, fieldName = "email")
	private String email;
	@NotBlank
	@Length(min = 6)
	private String senha;

	public NovoUsuarioRequest(@Email @NotBlank String email,
			@NotBlank @Length(min = 6) String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public Usuario toUsuario() {
		return new Usuario(email, hash());
	}

	public String getEmail() {
		return email;
	}
	
	public String hash() {
		return new BCryptPasswordEncoder().encode(senha);
	}

}
