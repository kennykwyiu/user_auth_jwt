package com.kenny.user_auth.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserRegistrationService userRegistrationService;

	private final LoginService loginService;

	private final SessionAuthSupport sessionAuthSupport;

	public AuthController(
			UserRegistrationService userRegistrationService,
			LoginService loginService,
			SessionAuthSupport sessionAuthSupport) {
		this.userRegistrationService = userRegistrationService;
		this.loginService = loginService;
		this.sessionAuthSupport = sessionAuthSupport;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(
			@RequestBody RegisterRequest request,
			HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		UserResponse body = userRegistrationService.register(request);
		sessionAuthSupport.signIn(httpRequest, httpResponse, body);
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}


}
