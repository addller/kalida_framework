package com.kalida.resources;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kalida.dto.UserDTO;
import com.kalida.dto.UserNewDTO;
import com.kalida.dto.UserProfileDTO;
import com.kalida.dto.UserUpadteProfileDTO;
import com.kalida.exception.DomainException;
import com.kalida.model.User;
import com.kalida.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserResource extends Controllable{

    @Autowired
    private UserService userService;

    @Autowired
    private AuthResource authController;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public List<UserDTO> findAll(){
        return userService.findAll().stream()
            .map(user -> modelMapper.map(user, UserDTO.class))
            .toList();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> create(@Valid @RequestBody UserNewDTO userNewDTO) {
        User user = modelMapper.map(userNewDTO, User.class);
        user.setPermissions(new ArrayList<>());

        String password = userNewDTO.getPassword();
        password = authController.encodePassword(password);
        user.setPassword(password);

        User newUser = userService.save(user);
        return authController.createToken(newUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        User user = userService.findById(id);
        userService.delete(user);
    }

    @GetMapping("/{id}")
    public UserDTO findbyId(@PathVariable Long id){
        User user = userService.findById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    @PutMapping
    public ResponseEntity<Void> editProfile(@Valid @RequestBody UserUpadteProfileDTO userUpdateProfileDTO){
        User userRequest = getUser();
        User user = userService.findById(userRequest.getId());
        modelMapper.map(userUpdateProfileDTO, user);
        userService.save(user);
        return authController.createToken(user);
    } 
    
    //remove this end point
    @PutMapping("/other_profile")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void editOtherProfile(@Valid @RequestBody UserProfileDTO userDTO){
        if(userDTO.getId().equals(getUser().getId())){
            throw new DomainException("Use edit profile in menu options to do this action", HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(userDTO.getId());
        modelMapper.map(userDTO, user);
        userService.save(user);
    } 

    
}
