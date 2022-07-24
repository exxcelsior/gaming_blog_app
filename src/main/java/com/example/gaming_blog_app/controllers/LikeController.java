package com.example.gaming_blog_app.controllers;

import com.example.gaming_blog_app.models.Comment;
import com.example.gaming_blog_app.models.Like;
import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.models.User;
import com.example.gaming_blog_app.services.LikeService;
import com.example.gaming_blog_app.services.PostService;
import com.example.gaming_blog_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/like/{postId}")
    @PreAuthorize("isAuthenticated()")
    public String doLike(@PathVariable int postId, Principal principal, Model model) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());
        Optional<Post> optionalPost = postService.getById(postId);

        if (optionalUser.isPresent() && optionalPost.isPresent()) {
            User user = optionalUser.get();
            Post post = optionalPost.get();

            if (likeService.existsByPostIdAndUserId(post.getId(), user.getId())) {
                likeService.delete(post, user);
            } else {
                Like like = new Like();
                like.setUser(user);
                like.setPost(post);

                likeService.save(like);
            }
            model.addAttribute("post", post);
            return "redirect:/posts/" + postId;
        } else {
            return "404";
        }
    }

    @GetMapping("/removelike/{postId}")
    @PreAuthorize("isAuthenticated()")
    public void removeLike(@PathVariable int postId, User user) {
        Optional<Post> post = postService.getById(postId);
        likeService.delete(post.get(), user);
    }
}
