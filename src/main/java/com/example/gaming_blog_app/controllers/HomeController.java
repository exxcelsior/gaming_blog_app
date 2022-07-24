package com.example.gaming_blog_app.controllers;

import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.models.User;
import com.example.gaming_blog_app.repositories.PostRepository;
import com.example.gaming_blog_app.services.CommentService;
import com.example.gaming_blog_app.services.LikeService;
import com.example.gaming_blog_app.services.PostService;
import com.example.gaming_blog_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private PostRepository postRepository;

    @GetMapping("/")
    public String home(@RequestParam Optional<Integer> page, Model model, Principal principal) {
        Page<Post> posts = postService.findAllByOrderedByCreateDateDesc(PageRequest.of(page.orElse(0), 5));

        // counts number of commentaries for each post to output it in view
        Map<Integer, Integer> commentCounts = new HashMap<>();
        for(int i = 0; i < posts.getContent().size(); i++) {
            Integer postId = posts.getContent().get(i).getId();
            commentCounts.put((postId), commentService.getNumberOfCommentsByPost(postId));
        }

        // count number of likes for each post to output it in view
        Map<Integer, Integer> likeCounts = new HashMap<>();
        for(int i = 0; i < posts.getContent().size(); i++) {
            Integer postId = posts.getContent().get(i).getId();
            likeCounts.put((postId), likeService.getNumberOfLikesByPost(postId));
        }

        model.addAttribute("likeCounts", likeCounts);
        model.addAttribute("commentCounts", commentCounts);
        model.addAttribute("posts", posts);
        return "home";
    }
}
