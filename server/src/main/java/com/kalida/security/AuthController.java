package com.kalida.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.model.Password;
import com.kalida.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Value("${security.salt}")
    private String salt;

    @PostMapping("/signin")
    public ResponseEntity<Void> signin(@RequestBody AccountCredentials data) {
        try {
            String username = data.getUsername().toLowerCase();
            String password = data.getPassword();
            
            authenticate(username, password);
            User user = userRepository.findByUsername(username);
            
            if (user != null) {
                return createToken(user);
            }
            
            throw new UsernameNotFoundException("User name not found: " + username);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("User name or password invalid");
        }
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody Password password){
        User userOld = UserService.autenthicated();
        authenticate(userOld.getUsername(), password.getCurrentPassword());
        
        User user = userService.findById(userOld.getId());
        user.setPassword(encodePassword(password.getNewPassword()));
        userService.save(user);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> createToken(User user) {
        String token = tokenProvider.createToken(user);
        return ResponseEntity.noContent()
                .header("access-control-expose-headers", "Authorization")
                .header("Authorization", "Bearer " + token)
                .build();
    }

    public void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password+salt));
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<Void> refreshToken() {
        User user = UserService.autenthicated();
        return createToken(user);
    }

    public String encodePassword(String password){
        return new BCryptPasswordEncoder(16).encode(password + salt);
    }


}
