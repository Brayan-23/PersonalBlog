package com.generation.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_posts")
public class Posts {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank(message = "The Title attribute is Mandatory!")
  @Size(min = 5, max = 100, message = "\n" +
      "the attribute title must have minimum 05 and maximum 100 characters\n")
  private String title;

  @NotBlank(message = "The Text attribute is Mandatory!")
  @Size(min = 10, max = 1000, message = "\n" +
      "the attribute title must have minimum 10 and maximum 1000 characters\n")
  private String text;

  @UpdateTimestamp
  private LocalDateTime date;

  @ManyToOne
  @JsonIgnoreProperties("posts")
  private Theme theme;

  @ManyToOne
  @JsonIgnoreProperties("posts")
  private User user;

  public long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }
}
