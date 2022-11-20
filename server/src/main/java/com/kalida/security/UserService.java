package com.kalida.security;

import com.kalida.exception.DomainException;
import com.kalida.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public final class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserService(UserRepository repository) {
		this.userRepository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User Name " + username + " not found");
	}

	public static User autenthicated(){
		return (User) SecurityContextHolder
        	.getContext()
        	.getAuthentication()
        	.getPrincipal();
    }

	public User findById(Long id){
		return userRepository.findById(id).orElseThrow(() -> new DomainException("user_not_found", HttpStatus.NOT_FOUND));
	}

    public User save(User user) {
		return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

	public void delete(User user) {
		userRepository.delete(user);
	}
}
