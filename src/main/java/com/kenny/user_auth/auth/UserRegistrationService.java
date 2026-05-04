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

}
