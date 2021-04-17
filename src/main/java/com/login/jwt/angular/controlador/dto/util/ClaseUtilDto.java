package com.login.jwt.angular.controlador.dto.util;

import com.login.jwt.angular.controlador.dto.RoleDto;
import com.login.jwt.angular.controlador.dto.UsuarioDto;
import com.login.jwt.angular.entidades.Role;
import com.login.jwt.angular.entidades.Usuario;

public class ClaseUtilDto {

	public static UsuarioDto getUsuarioDto(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(usuario.getId());
		usuarioDto.setIdentificacion(usuario.getIdentificacion());
		usuarioDto.setNombre(usuario.getNombre());
		usuarioDto.setNombreUsuario(usuario.getNombreUsuario());
		usuarioDto.setContrasenia(usuario.getContrasenia());
		usuarioDto.setEdad(usuario.getEdad());
		usuarioDto.setCiudad(usuario.getCiudad());
		return usuarioDto;
	}

	public static RoleDto getRoleDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setNombre(role.getNombre());
		return roleDto;
	}
}
