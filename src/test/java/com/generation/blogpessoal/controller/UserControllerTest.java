package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.User;
import com.generation.blogpessoal.repository.UserRepository;
import com.generation.blogpessoal.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void start() {
    userRepository.deleteAll();
    userService.registerUser(new User(
        0L,
        "Bryan Santos",
        "brayan@email.com",
        "12345678",
        "bbbbbbbbbbbbb",
        null));
  }

  @Test
  @DisplayName("Post new User")
  void postUser(){

    HttpEntity<User> bodyRequest = new HttpEntity<>(new User(1L,
        "Teste",
        "teste@email.com",
        "123456789",
        "ttttttttttttttttttt",
        null));

    ResponseEntity<User> bodyResponse = testRestTemplate.exchange("/user/register", HttpMethod.POST, bodyRequest, User.class);

    assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());
  }

  @Test
  @DisplayName("Not Duplicate User")
  void duplicateUser(){

    userService.registerUser(new User(1L,
        "Rogerio Algo",
        "rogerio@email.com",
        "87654321",
        "rrrrrrrrrrrrrrrrr",
        null));

    HttpEntity<User> bodyRequest = new HttpEntity<>(new User(1L,
        "Rogerio Algo",
        "rogerio@email.com",
        "87654321",
        "rrrrrrrrrrrrrrrrr",
        null));

    ResponseEntity<User> bodyResponse = testRestTemplate.exchange("/user/register", HttpMethod.POST, bodyRequest, User.class);

    assertEquals(HttpStatus.BAD_REQUEST, bodyResponse.getStatusCode());
  }


  @Test
  @DisplayName("Update User with Success")
  void updateUser(){
    Optional<User> userRegistered = userService.registerUser(new User(
        0L,
        "Update",
        "update@email.com",
        "987654321",
        "uuuuuuuuuuuuuuuuuuuuuuuuu",
        null));
    User userUpdate =
        new User(
            userRegistered.get().getId(),
            "Juliana Andrews Ramos",
            "juliana@email.com",
            "juliana123",
            "jjjjjj",
            null);
    HttpEntity<User> bodyRequest = new HttpEntity<>(userUpdate);

    ResponseEntity<User> bodyResponse = testRestTemplate
        .withBasicAuth("brayan@email.com", "12345678")
        .exchange("/user/update", HttpMethod.PUT, bodyRequest, User.class);

    assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
  }

  @Test
  @DisplayName("return all users that are in the database")
  void getAll() {
    userService.registerUser(new User(
        1L,
        "Juliana Andrews Ramos",
        "juliana@email.com",
        "juliana123",
        "jjjjjj",
        null));

    ResponseEntity<String> bodyResponse = testRestTemplate
        .withBasicAuth("brayan@email.com", "12345678")
        .exchange("/user/all", HttpMethod.GET, null, String.class);

    assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
  }



}
