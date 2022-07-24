package com.example.gaming_blog_app.controllers;

import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.models.User;
import com.example.gaming_blog_app.services.CommentService;
import com.example.gaming_blog_app.services.LikeService;
import com.example.gaming_blog_app.services.PostService;
import com.example.gaming_blog_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/blog/{username}")
    public String userBlog(@PathVariable String username, Model model) {
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Post> posts = postService.findAllByUser(user.getId());

            Map<Integer, Integer> commentCounts = new HashMap<>();
            for(int i = 0; i < posts.size(); i++) {
                Integer postId = posts.get(i).getId();
                commentCounts.put((postId), commentService.getNumberOfCommentsByPost(postId));
            }

            Map<Integer, Integer> likeCounts = new HashMap<>();
            for(int i = 0; i < posts.size(); i++) {
                Integer postId = posts.get(i).getId();
                likeCounts.put((postId), likeService.getNumberOfLikesByPost(postId));
            }


            model.addAttribute("likeCounts", likeCounts);
            model.addAttribute("commentCounts", commentCounts);
            model.addAttribute("posts", posts);
            model.addAttribute("user", user);
            return "/blog";
        } else {
            return "404";
        }
    }
}
