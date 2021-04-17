package com.login.jwt.angular.autenticacion.service;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JWTService {

    String crearToken(Authentication auth) throws IOException;

    Map<String, Claim> verificar(String token);

    String getNombreUsuario(String token);

    List<GrantedAuthority> getPrivilegios(String token);

}
