package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import com.generation.blogpessoal.model.User;
import com.generation.blogpessoal.model.UserLogin;
import com.generation.blogpessoal.repository.UserRepository;
import com.generation.blogpessoal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/all")
  public ResponseEntity <List<User>> getAll(){

    return ResponseEntity.ok(userRepository.findAll());

  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getById(@PathVariable Long id) {
    return userRepository.findById(id)
        .map(resposta -> ResponseEntity.ok(resposta))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/login")
  public ResponseEntity<UserLogin> authenticateUser(@RequestBody Optional<UserLogin> userLogin){

    return userService.authenticateUser(userLogin)
        .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
        .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }


  @PostMapping("/register")
  public ResponseEntity<User> postUser(@RequestBody @Valid User user) {

    return userService.registerUser(user)
        .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
        .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

  }

  @PutMapping("/update")
  public ResponseEntity<User> putUsuario(@Valid @RequestBody User user) {

    return userService.updateUser(user)
        .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

  }

}