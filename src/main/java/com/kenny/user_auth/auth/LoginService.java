package com.kenny.user_auth.auth;

import com.kenny.user_auth.repository.UserRepository;
import com.kenny.user_auth.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class LoginService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Verifies credentials and returns a safe user view. Throws 401 for unknown email or wrong password (same message).
	 */
	public UserResponse login(LoginRequest request) {
		if (request == null || request.email() == null || request.password() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
		}
		String email = request.email().trim().toLowerCase();
		if (email.isBlank() || request.password().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
		}

		User user = userRepository
				.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		}
		log.info("Login successful");
		return new UserResponse(user.getId(), user.getEmail(), user.getName());
	}

}
