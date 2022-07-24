package com.example.gaming_blog_app.repositories;

import com.example.gaming_blog_app.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(int id);

    Page<Post> findAllByOrderByCreateDateDesc(PageRequest pageRequest);

    Page<Post> findAllByCategory_IdOrderByCreateDateDesc(PageRequest pageRequest, int id);

    Page<Post> findAllByUserIdOrderByCreateDateDesc(PageRequest pageRequest, int id);

}
