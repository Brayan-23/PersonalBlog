package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "The name attribute is Mandatory!")
  private String name;

  @NotNull(message = "The usr attribute is Mandatory!")
  @Email(message = "The usr attribute must be a valid email!")
  @Schema(example = "email@email.com.br")
  private String usr;

  @NotBlank(message = "The password attribute is Mandatory!")
  @Size(min = 8, message = "The password must have at least 8 characters!")
  private String password;

  @Size(max = 5000, message = "The link of the photo cannot have more than 5000 characters!")
  private String photo;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
  @JsonIgnoreProperties("user")
  private List<Posts> posts;

  public User() {
  }

  public User(Long id, String name, String usr, String password, String photo, List<Posts> posts) {
    this.id = id;
    this.name = name;
    this.usr = usr;
    this.password = password;
    this.photo = photo;
    this.posts = posts;
  }


  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsr() {
    return usr;
  }

  public void setUsr(String usr) {
    this.usr = usr;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword (String password) {
    this.password = password;
  }

  public String getPhoto() {
    return this.photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public List<Posts> getPosts() {
    return this.posts;
  }

  public void setPosts(List<Posts> posts) {
    this.posts = posts;
  }

}