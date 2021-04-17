package com.login.jwt.angular.servicio;

import com.login.jwt.angular.controlador.dto.UsuarioDto;

public interface IUsuarioServicio {
	
    UsuarioDto guardarUsuario(UsuarioDto usuarioDto) throws Exception;

    UsuarioDto findUserByNombreUsuario(String nombreUsuario);

}
