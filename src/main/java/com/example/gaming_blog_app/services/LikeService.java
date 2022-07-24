package com.example.gaming_blog_app.services;

import com.example.gaming_blog_app.models.Like;
import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.models.User;
import com.example.gaming_blog_app.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public Like save(Like like) {
        return likeRepository.saveAndFlush(like);
    }

    public void delete(Post post, User user) {
        likeRepository.deleteByPostAndUser(post, user);
    }

    public Boolean existsByPostIdAndUserId(int postId, int userId) {
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    public int getNumberOfLikesByPost(int id) {
        return likeRepository.countLikesByPostId(id);
    }
}
