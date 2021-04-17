package com.login.jwt.angular.autenticacion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.login.jwt.angular.autenticacion.service.AuthenticationService;
import com.login.jwt.angular.controlador.dto.UsuarioDto;
import com.login.jwt.angular.servicio.IUsuarioServicio;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private IUsuarioServicio usuarioServicio;

	@Override
	public UsuarioDto findCurrentUserData() {
		return usuarioServicio.findUserByNombreUsuario(
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}

}
