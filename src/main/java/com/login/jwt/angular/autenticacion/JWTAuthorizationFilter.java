package com.login.jwt.angular.autenticacion;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.interfaces.Claim;
import com.login.jwt.angular.autenticacion.service.JWTService;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	private JWTService jwtService;

	public JWTAuthorizationFilter(ProviderManager providerManager) {
		super(providerManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String tokenHeader = request.getHeader("Authorization");
		if (tokenHeader == null || !request.getRequestURI().startsWith("/")) {
			chain.doFilter(request, response);
			return;
		}
		if (tokenHeader.startsWith("Bearer ")) {
			tokenHeader = tokenHeader.replaceAll("Bearer ", "");
		}
		Map<String, Claim> claims = jwtService.verificar(tokenHeader);
		if (claims == null) {
			throw new AuthorizationServiceException("Invalid Token");
		}
		String username = jwtService.getNombreUsuario(tokenHeader);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				tokenHeader, jwtService.getPrivilegios(tokenHeader));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		request.setAttribute("__spring_security_scpf_applied", true);
		chain.doFilter(request, response);
	}
}
