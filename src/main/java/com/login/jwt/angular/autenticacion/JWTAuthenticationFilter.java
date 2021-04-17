package com.login.jwt.angular.autenticacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.jwt.angular.autenticacion.service.JWTService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JWTService jwtService;

    @Autowired
    public JWTAuthenticationFilter(ProviderManager providerManager) {
        this.setAuthenticationManager(providerManager);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String content = IOUtils.toString(request.getReader());
            LoginRequest loginRequest = objectMapper.readValue(content, LoginRequest.class);
            final String userName = loginRequest.getNombreUsuario();
            final String password = loginRequest.getContrasenia();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
            this.setDetails(request, usernamePasswordAuthenticationToken);
            request.setAttribute("__spring_security_scpf_applied", true);
            return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            throw new RuntimeException("RequestBody could not be read");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String jwtToken = jwtService.crearToken(authResult);
        response.addHeader("Authorization", "Bearer " + jwtToken);
        Map<String, Object> body = new HashMap<>();
        body.put("token", jwtToken);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Credenciales incorrectas");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class LoginRequest {
        private String nombreUsuario;
        private String contrasenia;
    }

}
