package nl.natuurverhaal.natuurverhaal.controllers;




import nl.natuurverhaal.natuurverhaal.dtos.InputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.services.BlogPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/blog-posts")
public class BlogPostController {
    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }



    @GetMapping("/{username}/{id}")
    public ResponseEntity<OutputBlogpostDto> getBlogPost(@PathVariable("username") String username, @PathVariable("id") Long id) {
        OutputBlogpostDto blogPost = blogPostService.getBlogPost(username, id);
        return ResponseEntity.ok(blogPost);
    }

    @GetMapping("/{username}")
    public ResponseEntity <List<OutputBlogpostDto>> getBlogPostByUsername(@PathVariable("username") String username) {
        List<OutputBlogpostDto> blogPost = blogPostService.getBlogPostByUsername(username);
        return ResponseEntity.ok(blogPost);
    }

//    @GetMapping
//    public ResponseEntity<List<OutputBlogpostDto>> getAllBlogs() {
//        List<OutputBlogpostDto> blogPost = blogPostService.getAllBlogs();
//        return ResponseEntity.ok(blogPost);
//    }

    @GetMapping
    public ResponseEntity<List<OutputBlogpostDto>> getAllBlogs() {
        List<OutputBlogpostDto> blogPost = blogPostService.getAllBlogs();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(blogPost);
    }

    @PostMapping("/{username}")
    public ResponseEntity<OutputBlogpostDto> createBlogPost(@RequestPart("file") MultipartFile file,
                                                            @RequestPart("username") String username,
                                                            @RequestPart("caption") String caption,
                                                            @RequestPart("title") String title,
                                                            @RequestPart("subtitle") String subtitle,
                                                            @RequestPart("content") String content) throws IOException {

        System.out.println("file: " + file);
        System.out.println("username: " + username);
        System.out.println("caption: " + caption);
        InputBlogpostDto blogPost = new InputBlogpostDto();
        blogPost.setCaption(caption);
        blogPost.setTitle(title);
        blogPost.setSubtitle(subtitle);
        blogPost.setContent(content);
        blogPost.setUsername(username);
        blogPost.setFile(file);
        OutputBlogpostDto createdPost = blogPostService.createBlogPost(blogPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

//    @PostMapping("/{username}")
//    public ResponseEntity<OutputBlogpostDto> createBlogPost(@RequestBody InputBlogpostDto blogPost) {
//        OutputBlogpostDto createdPost = blogPostService.createBlogPost(blogPost);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
//    }


}
