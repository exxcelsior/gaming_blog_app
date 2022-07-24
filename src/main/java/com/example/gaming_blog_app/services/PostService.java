package com.example.gaming_blog_app.services;

import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.repositories.CommentRepository;
import com.example.gaming_blog_app.repositories.LikeRepository;
import com.example.gaming_blog_app.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Optional<Post> getById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> getAll(PageRequest of) {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        post.setCreateDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post update(Post post) {
        post.setUpdateDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void delete(Post post) {
        likeRepository.deleteAllByPost(post);
        commentRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }

    public Page<Post> findAllByOrderedByCreateDateDesc(PageRequest pageRequest) {
        return postRepository.findAllByOrderByCreateDateDesc(pageRequest);
    }

    public Page<Post> findAllByCategory(PageRequest pageRequest, int id) {
        return postRepository.findAllByCategory_IdOrderByCreateDateDesc(pageRequest, id);
    }

    public Page<Post> findAllByUser(PageRequest pageRequest, int id) {
        return postRepository.findAllByUserIdOrderByCreateDateDesc(pageRequest, id);
    }
}
