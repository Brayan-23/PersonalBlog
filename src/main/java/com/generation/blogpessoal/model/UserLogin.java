package com.generation.blogpessoal.model;

public class UserLogin {

  private Long id;
  private String name;
  private String usr;
  private String password;
  private String photo;
  private String token;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getUsr() {
    return usr;
  }

  public void setUsr(String usr) {
    this.usr = usr;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}