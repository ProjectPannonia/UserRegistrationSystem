package com.apress.ravi.UserRegistrationSystem.rest;


import com.apress.ravi.UserRegistrationSystem.dto.UserDTO;
import com.apress.ravi.UserRegistrationSystem.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {
    public static final Logger logger = LoggerFactory.getLogger(UserRegistrationRestController.class);

    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository){
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> listAllUsers(){
        List<UserDTO> users = userJpaRepository.findAll();
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }
    //Create new user
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO user){
        userJpaRepository.save(user);
        return new ResponseEntity<UserDTO>(user,HttpStatus.CREATED);
    }
    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id){
        UserDTO user = userJpaRepository.findById(id).orElse(new UserDTO());
        return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
    }
    // Get user by name
    @GetMapping("/name/{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable("name") final String name){
        UserDTO user = userJpaRepository.findByName(name);
        return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
    }
    // Update user by id
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable final Long id, @RequestBody UserDTO user){
        UserDTO currentUser = userJpaRepository.findById(id).orElse(null);
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        userJpaRepository.saveAndFlush(currentUser);
        return new ResponseEntity<UserDTO>(currentUser,HttpStatus.OK);
    }
    // Delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id){
        userJpaRepository.deleteById(id);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }
    // Delet user by name
    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity<UserDTO> deleteUserByName(@PathVariable("name") final String name){
        UserDTO user = userJpaRepository.findByName(name);
        userJpaRepository.delete(user);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }
}
