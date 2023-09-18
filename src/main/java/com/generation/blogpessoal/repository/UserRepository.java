package com.generation.blogpessoal.repository;

import java.util.Optional;

import com.generation.blogpessoal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

   Optional<User> findByUsr(String user);

}