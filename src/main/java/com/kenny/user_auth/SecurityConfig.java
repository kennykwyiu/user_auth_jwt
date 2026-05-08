package com.kenny.user_auth;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/",
								"/index.html",
								"/register.html",
								"/login.html",
								"/registration-success.html",
								"/css/**",
								"/api/hello",
								"/api/auth/register",
								"/api/auth/login",
								"/api/auth/logout"
						).permitAll()
						.anyRequest().authenticated())
				.httpBasic(AbstractHttpConfigurer::disable)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				.exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
					if (response.isCommitted()) {
						return;
					}
					String uri = request.getRequestURI();
					if (uri != null && uri.endsWith(".html")) {
						response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login.html"));
						return;
					}
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}))
				.csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/**"));

		return http.build();
	}

}
