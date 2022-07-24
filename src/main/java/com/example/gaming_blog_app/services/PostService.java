package com.example.gaming_blog_app.services;

import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.repositories.CommentRepository;
import com.example.gaming_blog_app.repositories.LikeRepository;
import com.example.gaming_blog_app.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        post.setCreateDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void delete(Post post) {
        likeRepository.deleteAllByPost(post);
        commentRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }

    public List<Post> findAllByOrderedByCreateDateDesc() {
        return postRepository.findAllByOrderByCreateDateDesc();
    }

    public List<Post> findAllByCategory(int id) {
        return postRepository.findAllByCategory_Id(id);
    }

    public List<Post> findAllByUser(int id) {
        return postRepository.findAllByUserId(id);
    }

    public Map<Integer,Integer> mapCommentsCountToPost(List<Post> posts) {
        List<Post> _posts = findAllByOrderedByCreateDateDesc();
        Map<Integer, Integer> commentCounts = new HashMap<>();

        for(int i = 0; i < _posts.size(); i++) {
            Integer postId = _posts.get(i).getId();
            commentCounts.put((postId), commentRepository.countCommentByPostId(postId));
        }
        return commentCounts;
    }
}
