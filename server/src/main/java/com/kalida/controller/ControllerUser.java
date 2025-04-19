package com.kalida.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.dto.UserDTO;
import com.kalida.dto.UserNewDTO;
import com.kalida.dto.UserProfileDTO;
import com.kalida.response.CSV;
import com.kalida.security.AuthController;
import com.kalida.security.User;
import com.kalida.security.UserService;

@RestController
@RequestMapping("/users")
public class ControllerUser extends Controllable{

    @Autowired
    private UserService userService;

    @Autowired
    private AuthController authController;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${security.salt}")
    private String salt;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserNewDTO userNewDTO) {
        User user = modelMapper.map(userNewDTO, User.class);
        user.setPermissions(new ArrayList<>());

        String password = userNewDTO.getPassword();
        password = authController.encodePassword(password);
        user.setPassword(password);

        User newUser = userService.save(user);
        return authController.createToken(newUser);
    }
    
    @GetMapping("/all")
    public CSV getAll(){
        Long userId = getUser().getId();
        List<User> users = userService.findAll();
        users.removeIf(user -> user.getId().equals(userId));
        
        CSV csv = new CSV("id", "name", "username", "nickName");
        users.stream().forEach(user -> 
            csv.addRow(user.getId(), user.getName(), user.getUsername(), user.getNickName())
        );
        return csv;
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        User user = userService.findById(id);
        userService.delete(user);
        return noContent();
    }

    @GetMapping("/{id}")
    public UserDTO findbyId(@PathVariable Long id){
        User user = userService.findById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    @PutMapping
    public ResponseEntity<Void> editProfile(@RequestBody UserProfileDTO userDTO){
        User user = saveUser(userDTO);
        return authController.createToken(user);
    } 
    
    //remove this end point
    @PutMapping("/other_profile")
    public ResponseEntity<Void> editOtherProfile(@RequestBody UserProfileDTO userDTO){
        saveUser(userDTO);
        return noContent();
    } 


    private User saveUser(UserProfileDTO userProfileDTO){
        User user = userService.findById(userProfileDTO.getId());
        user.setName(userProfileDTO.getName());
        user.setEmail(userProfileDTO.getEmail());  
        user.setLang(userProfileDTO.getLang());
        user.setNickName(userProfileDTO.getNickName());
        user.setUsername(userProfileDTO.getUsername());
        userService.save(user);
        return user;
    }
    
}
