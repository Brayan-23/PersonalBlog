package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Posts;
import com.generation.blogpessoal.repository.PostsRepository;
import com.generation.blogpessoal.repository.ThemeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostsController {

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private ThemeRepository themeRepository;

  @GetMapping
  public ResponseEntity<List<Posts>> getAll() {
    return ResponseEntity.ok(postsRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Posts> getById(@PathVariable Long id) {
    return postsRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/title/{title}")
  public ResponseEntity<List<Posts>> getById(@PathVariable String title) {
    return ResponseEntity.ok(postsRepository.findAllByTitleContainingIgnoreCase(title));
  }

  @PostMapping
  ResponseEntity<Posts> create(@Valid @RequestBody Posts post){
    if(themeRepository.existsById(post.getTheme().getId())){
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(postsRepository.save(post));
    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Theme not Exists", null);
  }

  @PutMapping
  ResponseEntity<Posts> update(@Valid @RequestBody Posts posts){
   if (postsRepository.existsById(posts.getId())){
     if (themeRepository.existsById(posts.getTheme().getId())){
       return ResponseEntity.status(HttpStatus.OK).body(postsRepository.save(posts));
     }

     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Theme not Exists", null);
   }
   return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  void delete(@PathVariable Long id){
    Optional<Posts> post = postsRepository.findById(id);

    if (post.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
    postsRepository.deleteById(id);
  }

}
