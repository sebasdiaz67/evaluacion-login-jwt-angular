package com.login.jwt.angular.autenticacion;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.login.jwt.angular.controlador.dto.UsuarioDto;
import com.login.jwt.angular.servicio.IUsuarioServicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class BackAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IUsuarioServicio userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsuarioDto usuarioDto = userService.findUserByNombreUsuario(authentication.getPrincipal().toString());
		//passwordEncoder.encode(usuarioDto.getContrasenia());
		if (usuarioDto == null
				|| !passwordEncoder.matches(authentication.getCredentials().toString(), usuarioDto.getContrasenia())) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(usuarioDto, null, new HashSet<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UserAuthentication {
		private String nombreUsuario;
		private String contrasenia;
	}
}
