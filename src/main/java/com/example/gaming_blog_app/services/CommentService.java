package com.example.gaming_blog_app.services;

import com.example.gaming_blog_app.models.Comment;
import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    public List<Comment> getAll(int id) {
        return commentRepository.findAllByPostIdOrderByCreateDateDesc(id);
    }

    public int getNumberOfCommentsByPost(int id) {
        return commentRepository.countCommentByPostId(id);
    }
}
