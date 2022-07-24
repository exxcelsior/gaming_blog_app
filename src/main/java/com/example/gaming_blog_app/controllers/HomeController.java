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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        List<Post> posts = postService.findAllByOrderedByCreateDateDesc();

        // counts number of commentaries for each post to output it in view
        Map<Integer, Integer> commentCounts = new HashMap<>();
        for(int i = 0; i < posts.size(); i++) {
            Integer postId = posts.get(i).getId();
            commentCounts.put((postId), commentService.getNumberOfCommentsByPost(postId));
        }

        // count number of likes for each post to output it in view
        Map<Integer, Integer> likeCounts = new HashMap<>();
        for(int i = 0; i < posts.size(); i++) {
            Integer postId = posts.get(i).getId();
            likeCounts.put((postId), likeService.getNumberOfLikesByPost(postId));
        }


        model.addAttribute("likeCounts", likeCounts);
        model.addAttribute("commentCounts", commentCounts);
        model.addAttribute("posts", posts);
        return "home";
    }
}
