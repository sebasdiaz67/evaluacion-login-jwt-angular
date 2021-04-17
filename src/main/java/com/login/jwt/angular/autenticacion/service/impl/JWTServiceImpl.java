package com.login.jwt.angular.autenticacion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.login.jwt.angular.autenticacion.service.JWTService;
import com.login.jwt.angular.controlador.dto.UsuarioDto;

@Service
public class JWTServiceImpl implements JWTService {

    @Override
    public String crearToken(Authentication auth) {
        UsuarioDto usuarioDto = (UsuarioDto) auth.getPrincipal();
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("roles", auth.getAuthorities());
        return JWT.create()
                .withHeader(headerClaims)
                .withClaim("id", usuarioDto.getId())
                .withSubject(usuarioDto.getNombreUsuario())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(Algorithm.HMAC256("evaluacion"));
    }

    @Override
    public Map<String, Claim> verificar(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("evaluacion");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getNombreUsuario(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<GrantedAuthority> getPrivilegios(String token) {
        Map[] privilegesToken = JWT.decode(token).getHeaderClaim("roles").asArray(Map.class);
        List<GrantedAuthority> privileges = new ArrayList<>();
        for (Map<String, String> privilege : privilegesToken) {
            privileges.add(new SimpleGrantedAuthority(privilege.get("authority")));
        }
        return privileges;
    }

}
