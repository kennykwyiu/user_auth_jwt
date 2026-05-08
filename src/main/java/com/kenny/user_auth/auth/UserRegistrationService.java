package com.kenny.user_auth.auth;

import com.kenny.user_auth.repository.UserRepository;
import com.kenny.user_auth.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserRegistrationService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponse register(RegisterRequest request) {
		if (request == null || request.email() == null || request.password() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
		}
		String email = request.email().trim().toLowerCase();
		String rawPassword = request.password();
		if (email.isBlank() || rawPassword.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
		}
		if (userRepository.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
		}

		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(rawPassword));
		user.setName(nameFromEmail(email));

		User saved = userRepository.save(user);
		return new UserResponse(saved.getId(), saved.getEmail(), saved.getName());
	}

	private static String nameFromEmail(String email) {
		int at = email.indexOf('@');
		return at > 0 ? email.substring(0, at) : email;
	}

}
