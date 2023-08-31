package com.generation.blogpessoal.repository;

import com.generation.blogpessoal.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository  extends JpaRepository<Posts, Long> {

   List<Posts> findAllByTitleContainingIgnoreCase(@Param("title") String title);

}
