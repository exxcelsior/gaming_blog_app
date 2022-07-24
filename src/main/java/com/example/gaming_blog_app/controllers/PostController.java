package com.example.gaming_blog_app.controllers;

import com.example.gaming_blog_app.models.Category;
import com.example.gaming_blog_app.models.Comment;
import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.models.User;
import com.example.gaming_blog_app.repositories.CategoryRepository;
import com.example.gaming_blog_app.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable int id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);
        List<Comment> comments = commentService.getAll(id);

        int commentCount = commentService.getNumberOfCommentsByPost(id);
        int likeCount = likeService.getNumberOfLikesByPost(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            model.addAttribute("likeCount", likeCount);
            model.addAttribute("commentCount", commentCount);
            model.addAttribute("comments", comments);
            model.addAttribute("post", post);
            return "post";
        }
        else {
            return "404";
        }
    }

    @GetMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    public String createPost(Model model, Principal principal) {
        Optional<User> optionalUser = userService.findByUsername(principal.getName());
        List<Category> categories = categoryService.getAllInAscOrder();
        if (optionalUser.isPresent()) {
            Post post = new Post();
            post.setUser(optionalUser.get());
            model.addAttribute("categories", categories);
            model.addAttribute("post", post);
            return "new_post";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/posts/new")
    public String savePost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/update")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable int id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_update";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable int id, Post post, Principal principal) {
        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            if(isPrincipalOwnerOfPost(principal, existingPost)) {
                //model.addAttribute("post", post);
                //postService.save(post);
                existingPost.setTitle(post.getTitle());
                existingPost.setContent(post.getContent());

                postService.save(existingPost);
                return "redirect:/posts/" + post.getId();
            }
        }
        return "/404";
    }

    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable int id, Principal principal) {

        Optional<Post> optionalPost = postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (isPrincipalOwnerOfPost(principal, post)) {
                postService.delete(post);
                return "redirect:/";
            } else {
                return "/404";
            }

        } else {
            return "/404";
        }
    }

    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        return principal != null && principal.getName().equals(post.getUser().getUsername());
    }

    @GetMapping("/categories/{id}")
    public String getAllPostsByCategory(@PathVariable int id, Model model) {
        List<Post> posts = postService.findAllByCategory(id);

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
        return "/home";
    }
}
