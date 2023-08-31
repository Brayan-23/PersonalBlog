package com.generation.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "tb_themes")
public class Theme {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "The Attribute is Mandatory")
  private String description;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "theme", cascade = CascadeType.REMOVE)
  @JsonIgnoreProperties("theme")
  private List<Posts> posts;

  public List<Posts> getPosts() {
    return posts;
  }

  public void setPosts(List<Posts> posts) {
    this.posts = posts;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}