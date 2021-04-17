package com.login.jwt.angular.servicio.impl;

import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.login.jwt.angular.controlador.dto.RoleDto;
import com.login.jwt.angular.controlador.dto.UsuarioDto;
import com.login.jwt.angular.controlador.dto.util.ClaseUtilDto;
import com.login.jwt.angular.entidades.Role;
import com.login.jwt.angular.entidades.Usuario;
import com.login.jwt.angular.repositorio.IRoleRepositorio;
import com.login.jwt.angular.repositorio.IUsuarioRepositorio;
import com.login.jwt.angular.servicio.IUsuarioServicio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioServicio {

	private final IUsuarioRepositorio usuarioRepositorio;
	private final PasswordEncoder passwordEncoder;
	private final IRoleRepositorio roleRepositorio;

	@Override
	public UsuarioDto guardarUsuario(UsuarioDto usuarioDto) throws Exception {
		Optional<Usuario> usuarioBDD = usuarioRepositorio.findByNombreUsuario(usuarioDto.getNombreUsuario());
		if (usuarioBDD.isPresent()) {
			throw new Exception("El usuario ya existe en la base de datos");
		}
		Usuario usuario = new Usuario();
		usuario.setContrasenia(passwordEncoder.encode(usuarioDto.getContrasenia()));
		usuario.setNombreUsuario(usuarioDto.getNombreUsuario());
		usuario.setIdentificacion(usuarioDto.getIdentificacion());
		usuario.setEdad(usuarioDto.getEdad());
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setCiudad(usuarioDto.getCiudad());
		for (RoleDto roleDto : usuarioDto.getRoles()) {
			Role role = roleRepositorio.findByNombre(roleDto.getNombre()).orElse(new Role());
			role.setNombre(roleDto.getNombre());
			usuario.getRoles().add(role);
			roleRepositorio.save(role);
		}
		usuarioRepositorio.save(usuario);
		UsuarioDto usuarioDtoRespuesta = ClaseUtilDto.getUsuarioDto(usuario);
		usuario.getRoles().forEach(role -> {
			RoleDto roleDtoRespuesta = ClaseUtilDto.getRoleDto(role);
			usuarioDtoRespuesta.getRoles().add(roleDtoRespuesta);
		});
		return usuarioDtoRespuesta;
	}

	@Override
	public UsuarioDto findUserByNombreUsuario(String nombreUsuario) {
		Usuario usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new ValidationException("Usuario no encontrado"));
		UsuarioDto usuarioDto = ClaseUtilDto.getUsuarioDto(usuario);
		usuario.getRoles().forEach(role -> {
			RoleDto roleDto = ClaseUtilDto.getRoleDto(role);
			usuarioDto.getRoles().add(roleDto);
		});
		return usuarioDto;
	}
}
