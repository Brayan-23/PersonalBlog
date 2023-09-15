package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
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
public class UsuarioControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;
  @Autowired
  private UsuarioService usuarioService;
  @Autowired
  private UsuarioRepository usuarioRepository;

  @BeforeEach
  void start() {
    usuarioRepository.deleteAll();
    usuarioService.cadastrarUsuario(new Usuario(
        0L,
        "Brayan Santos",
        "brayan@email.com",
        "12345678",
        "bbbbbbbbbbbbb",
        null));
  }

  @Test
  @DisplayName("Post new User")
  void postUser(){

    HttpEntity<Usuario> bodyRequest = new HttpEntity<>(new Usuario(1L,
        "Teste",
        "teste@email.com",
        "123456789",
        "ttttttttttttttttttt",
        null));

    ResponseEntity<Usuario> bodyResponse = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, bodyRequest, Usuario.class);

    assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());
  }

  @Test
  @DisplayName("Not Duplicate User")
  void duplicateUser(){

    usuarioService.cadastrarUsuario(new Usuario(1L,
        "Rogerio Algo",
        "rogerio@email.com",
        "87654321",
        "rrrrrrrrrrrrrrrrr",
        null));

    HttpEntity<Usuario> bodyRequest = new HttpEntity<>(new Usuario(1L,
        "Rogerio Algo",
        "rogerio@email.com",
        "87654321",
        "rrrrrrrrrrrrrrrrr",
        null));

    ResponseEntity<Usuario> bodyResponse = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, bodyRequest, Usuario.class);

    assertEquals(HttpStatus.BAD_REQUEST, bodyResponse.getStatusCode());
  }


  @Test
  @DisplayName("Update User with Success")
  void updateUser(){
    Optional<Usuario> userRegistered = usuarioService.cadastrarUsuario(new Usuario(
        0L,
        "Update",
        "update@email.com",
        "987654321",
        "uuuuuuuuuuuuuuuuuuuuuuuuu",
        null));
    Usuario userUpdate =
        new Usuario(
            userRegistered.get().getId(),
            "Juliana Andrews Ramos",
            "juliana@email.com",
            "juliana123",
            "jjjjjj",
            null);
    HttpEntity<Usuario> bodyRequest = new HttpEntity<>(userUpdate);

    ResponseEntity<Usuario> bodyResponse = testRestTemplate
        .withBasicAuth("brayan@email.com", "12345678")
        .exchange("/usuarios/atualizar", HttpMethod.PUT, bodyRequest, Usuario.class);

    assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
  }

  @Test
  @DisplayName("return all users that are in the database")
  void getAll() {
    usuarioService.cadastrarUsuario(new Usuario(
            1L,
            "Juliana Andrews Ramos",
            "juliana@email.com",
            "juliana123",
            "jjjjjj",
            null));

    ResponseEntity<String> bodyResponse = testRestTemplate
        .withBasicAuth("brayan@email.com", "12345678")
        .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

    assertEquals(HttpStatus.OK, bodyResponse.getStatusCode());
  }




}
