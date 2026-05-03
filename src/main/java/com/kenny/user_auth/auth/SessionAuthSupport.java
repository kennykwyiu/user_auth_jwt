package com.kenny.user_auth.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SessionAuthSupport {

	private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

	/**
	 * Binds {@link SecurityContextHolder} into the servlet session so later {@code GET /blog-demo.html} is authenticated.
	 */
	public void signIn(HttpServletRequest request, HttpServletResponse response, UserResponse user) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user.email(),
				null,
				List.of(new SimpleGrantedAuthority("ROLE_USER"))
		);
		authentication.setDetails(user);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		securityContextRepository.saveContext(context, request, response);
	}

	/** Clears the security context and ends the servlet session (no-op if there is no session). */
	public void signOut(HttpServletRequest request) {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			log.debug("Session has been invalidated");
		}
	}

}
