package nl.natuurverhaal.natuurverhaal.controllers;


import lombok.Data;
import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/blog-posts")
public class BlogPostController {
    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }


    @GetMapping("/{username}")
    public ResponseEntity<List<BlogPost>> getAllBlogPosts() {
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        return ResponseEntity.ok(blogPosts);
    }

    @PostMapping("/{username}")
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost) {
        BlogPost createdPost = blogPostService.createBlogPost(blogPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }




}
