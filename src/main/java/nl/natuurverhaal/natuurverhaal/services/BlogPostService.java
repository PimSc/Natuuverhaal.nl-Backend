package nl.natuurverhaal.natuurverhaal.services;

import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.repositories.BlogPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    public BlogPost createBlogPost(BlogPost blogPost) {
        // Add any additional logic here before saving the blog post
        return blogPostRepository.save(blogPost);
    }


}
