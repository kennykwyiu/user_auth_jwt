package com.kenny.user_auth.repository;

import java.util.Optional;

import com.kenny.user_auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

}
