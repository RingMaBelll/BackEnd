package com.ringmabell.whichme_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringmabell.whichme_backend.entitiy.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByLoginId(String username);

	boolean existsByEmail(String email);

	Optional<User> findByLoginId(String username);
}
