package com.example.gaming_blog_app.repositories;

import com.example.gaming_blog_app.models.Like;
import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    int countLikesByPostId(int id);

    @Transactional
    int deleteByPostAndUser(@Param("post_id")Post post, @Param("user_id")User user);

    @Transactional
    void deleteAllByPost(Post post);

    Boolean existsByPostIdAndUserId(int postId, int userId);
}
