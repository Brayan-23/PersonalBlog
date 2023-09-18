package com.generation.blogpessoal.service;

import java.util.Objects;
import java.util.Optional;

import com.generation.blogpessoal.model.User;
import com.generation.blogpessoal.model.UserLogin;
import com.generation.blogpessoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.security.JwtService;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  public Optional<User> registerUser(User usuario) {

    if (userRepository.findByUsr(usuario.getUsr()).isPresent())
      return Optional.empty();

    usuario.setPassword(passwordEncrypt(usuario.getPassword()));

    return Optional.of(userRepository.save(usuario));

  }

  public Optional<User> updateUser(User user) {

    if(userRepository.findById(user.getId()).isPresent()) {

      Optional<User> buscaUsuario = userRepository.findByUsr(user.getUsr());

      if ( (buscaUsuario.isPresent()) && (!Objects.equals(buscaUsuario.get().getId(), user.getId())))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exists!", null);

      user.setPassword(passwordEncrypt(user.getPassword()));

      return Optional.of(userRepository.save(user));

    }

    return Optional.empty();

  }

  public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin) {

    // Gera o Objeto de autenticação
    var credenciais = new UsernamePasswordAuthenticationToken(userLogin.get().getUsr(), userLogin.get().getPassword());

    // Autentica o Usuario
    Authentication authentication = authenticationManager.authenticate(credenciais);

    // Se a autenticação foi efetuada com sucesso
    if (authentication.isAuthenticated()) {

      // Busca os dados do usuário
      Optional<User> usuario = userRepository.findByUsr(userLogin.get().getUsr());

      // Se o usuário foi encontrado
      if (usuario.isPresent()) {

        // Preenche o Objeto usuarioLogin com os dados encontrados
        userLogin.get().setId(usuario.get().getId());
        userLogin.get().setName(usuario.get().getName());
        userLogin.get().setPhoto(usuario.get().getPhoto());
        userLogin.get().setToken(generateToken(userLogin.get().getUsr()));
        userLogin.get().setPassword("");

        // Retorna o Objeto preenchido
        return userLogin;

      }

    }

    return Optional.empty();

  }

  private String passwordEncrypt(String password) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return encoder.encode(password);

  }

  private String generateToken(String user) {
    return "Bearer " + jwtService.generateToken(user);
  }

}