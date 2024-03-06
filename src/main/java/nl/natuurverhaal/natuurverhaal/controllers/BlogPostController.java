package nl.natuurverhaal.natuurverhaal.controllers;




import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.natuurverhaal.natuurverhaal.dtos.InputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.services.BlogPostService;
import nl.natuurverhaal.natuurverhaal.utils.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
                                                            @RequestPart("content") String content,
                                                            @RequestPart("categories") String categoriesJson)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Category> categories = new HashSet<>();
        Category c1 = Category.valueOf(categoriesJson);
        categories.add(c1);
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateAndTime.format(formatter);

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
        blogPost.setDate(formattedDateTime);
        blogPost.setCategories(categories);
        OutputBlogpostDto createdPost = blogPostService.createBlogPost(blogPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateBlogPost(@PathVariable("id") Long id,
                                               @RequestPart("file") MultipartFile file,
                                               @RequestPart("username") String username,
                                               @RequestPart("caption") String caption,
                                               @RequestPart("title") String title,
                                               @RequestPart("subtitle") String subtitle,
                                               @RequestPart("content") String content,
                                               @RequestPart("categories") String categoriesJson)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Category> categories = new HashSet<>();
        Category c1 = Category.valueOf(categoriesJson);
        categories.add(c1);
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateAndTime.format(formatter);

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
        blogPost.setDate(formattedDateTime);
        blogPost.setCategories(categories);

        blogPostService.updateBlogPost(id, blogPost);
        return ResponseEntity.noContent().build();
    }



    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable("username") String username, @PathVariable("id") Long id) {
        blogPostService.deleteBlogPost(username, id);
        return ResponseEntity.noContent().build();
    }


}



