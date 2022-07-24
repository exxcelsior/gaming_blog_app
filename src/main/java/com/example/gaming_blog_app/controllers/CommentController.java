package com.example.gaming_blog_app.controllers;

import com.example.gaming_blog_app.models.Comment;
import com.example.gaming_blog_app.models.Post;
import com.example.gaming_blog_app.models.User;
import com.example.gaming_blog_app.services.CommentService;
import com.example.gaming_blog_app.services.PostService;
import com.example.gaming_blog_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public String getAllComments(@PathVariable int id, Model model) {
        List<Comment> comments = commentService.getAll(id);
        int commentCount = commentService.getNumberOfCommentsByPost(id);
        model.addAttribute("comments", comments);
        model.addAttribute("commentCount", commentCount);
        return "comment";
    }

    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public String createNewPost(@Valid Comment comment,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/new_comment";

        } else {
            commentService.save(comment);
            return "redirect:/posts/" + comment.getPost().getId();
        }
    }

    @RequestMapping(value = "/commentPost/{id}", method = RequestMethod.GET)
    public String commentPostWithId(@PathVariable int id,
                                    Principal principal,
                                    Model model) {

        Optional<Post> post = postService.getById(id);

        if (post.isPresent()) {
            Optional<User> user = userService.findByUsername(principal.getName());

            if (user.isPresent()) {
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setPost(post.get());

                model.addAttribute("comment", comment);

                return "/new_comment";

            } else {
                return "/404";
            }

        } else {
            return "/404";
        }
    }
}
