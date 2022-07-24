package com.example.gaming_blog_app.repositories;

import com.example.gaming_blog_app.models.Comment;
import com.example.gaming_blog_app.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdOrderByCreateDateDesc(int id);

    int countCommentByPostId(int id);

    @Transactional
    void deleteAllByPost(Post post);
}
