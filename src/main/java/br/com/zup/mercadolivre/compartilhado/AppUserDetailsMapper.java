package br.com.zup.mercadolivre.compartilhado;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.zup.mercadolivre.cadastraousuario.Usuario;
import br.com.zup.mercadolivre.compartilhado.seguranca.UserDetailsMapper;

@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper{

	@Override
	public UserDetails map(Object shouldBeASystemUser) {						
		return new UsuarioLogado((Usuario)shouldBeASystemUser);
	}

}
