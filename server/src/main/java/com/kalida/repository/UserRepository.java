package com.kalida.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kalida.security.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

	User findByEmail(String email);
}
