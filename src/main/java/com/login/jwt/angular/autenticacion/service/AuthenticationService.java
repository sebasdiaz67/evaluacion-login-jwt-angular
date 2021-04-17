package com.login.jwt.angular.autenticacion.service;

import com.login.jwt.angular.controlador.dto.UsuarioDto;

public interface AuthenticationService {

    UsuarioDto findCurrentUserData();
}
