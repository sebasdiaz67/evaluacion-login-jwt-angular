package com.login.jwt.angular.controlador;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.jwt.angular.controlador.dto.UsuarioDto;
import com.login.jwt.angular.servicio.IUsuarioServicio;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsuarioControlador {
    private final IUsuarioServicio usuarioServicio;

    @PostMapping("/guardarUsuario")
    public ResponseEntity<?> guardarUsuario(@RequestBody @NotNull UsuarioDto usuarioDto) {
    	UsuarioDto usuarioDtoReturn = null;
    	Map<String, Object> response = new HashMap<>();
    	try {
    		usuarioDtoReturn = usuarioServicio.guardarUsuario(usuarioDto);
    	} catch (Exception e) {
    		response.put("mensaje", "Ocurrio un error al guardar");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	response.put("mensaje", "Se guardo con exito");
		response.put("usuario", usuarioDtoReturn);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

