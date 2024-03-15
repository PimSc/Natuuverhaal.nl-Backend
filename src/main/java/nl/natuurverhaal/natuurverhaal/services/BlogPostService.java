package nl.natuurverhaal.natuurverhaal.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.natuurverhaal.natuurverhaal.controllers.ExceptionController;
import nl.natuurverhaal.natuurverhaal.dtos.InputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.BlogPostRepository;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import nl.natuurverhaal.natuurverhaal.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;


    public BlogPostService(BlogPostRepository blogPostRepository, UserRepository userRepository) {
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
    }

    public List<BlogPost> getAllBlogPosts(String username, long id) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            user.get();

        } else {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
        return blogPostRepository.findAll();
    }

    public OutputBlogpostDto createBlogPost(InputBlogpostDto inputBlogpostDto) throws IOException {
        BlogPost blogPost = new BlogPost();

        blogPost.setCaption(inputBlogpostDto.getCaption());
        blogPost.setContent(inputBlogpostDto.getContent());
        blogPost.setSubtitle(inputBlogpostDto.getSubtitle());
        blogPost.setTitle(inputBlogpostDto.getTitle());
        blogPost.setImageData(inputBlogpostDto.getFile().getBytes());
        blogPost.setImageData(ImageUtil.compressImage(inputBlogpostDto.getFile().getBytes()));
        blogPost.setDate(inputBlogpostDto.getDate());
        blogPost.setCategories(inputBlogpostDto.getCategories());


        if (inputBlogpostDto.getUsername() != null) {
            User user = new User();
            user.setUsername(inputBlogpostDto.getUsername());
            blogPost.setUser(user);
        }

        blogPostRepository.save(blogPost);
        OutputBlogpostDto outputBlogpostDto = new OutputBlogpostDto();

        outputBlogpostDto.setCaption(blogPost.getCaption());
        outputBlogpostDto.setContent(blogPost.getContent());
        outputBlogpostDto.setSubtitle(blogPost.getSubtitle());
        outputBlogpostDto.setTitle(blogPost.getTitle());
        outputBlogpostDto.setUsername(blogPost.getUser().getUsername());
        outputBlogpostDto.setId(blogPost.getId());
        outputBlogpostDto.setDate(blogPost.getDate());
        outputBlogpostDto.setCategories(blogPost.getCategories());
        outputBlogpostDto.setFileContent(ImageUtil.decompressImage(blogPost.getImageData()));
        return outputBlogpostDto;
    }

    @Transactional
    public OutputBlogpostDto getBlogPost(String username, Long id) {

        BlogPost blogPost = blogPostRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new EntityNotFoundException("Blog post not found with username " + username + " and id " + id));

        OutputBlogpostDto outputBlogpostDto = new OutputBlogpostDto();
        outputBlogpostDto.setTitle(blogPost.getTitle());
        outputBlogpostDto.setSubtitle(blogPost.getSubtitle());
        outputBlogpostDto.setCaption(blogPost.getCaption());
        outputBlogpostDto.setContent(blogPost.getContent());
        outputBlogpostDto.setUsername(blogPost.getUser().getUsername());
        outputBlogpostDto.setId(blogPost.getId());
        outputBlogpostDto.setFileContent(ImageUtil.decompressImage(blogPost.getImageData()));
        outputBlogpostDto.setDate(blogPost.getDate());
        outputBlogpostDto.setCategories(blogPost.getCategories());
        return outputBlogpostDto;
    }

    @Transactional
    public List<OutputBlogpostDto> getAllBlogs() {
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        List<OutputBlogpostDto> outputBlogpostDtoList = new ArrayList<>();

        for (BlogPost blogPost : blogPostList) {
            OutputBlogpostDto outputBlogpostDto = new OutputBlogpostDto();
            outputBlogpostDto.setTitle(blogPost.getTitle());
            outputBlogpostDto.setSubtitle(blogPost.getSubtitle());
            outputBlogpostDto.setCaption(blogPost.getCaption());
            outputBlogpostDto.setFileContent(ImageUtil.decompressImage(blogPost.getImageData()));
            outputBlogpostDto.setContent(blogPost.getContent());
            outputBlogpostDto.setUsername(blogPost.getUser().getUsername());
            outputBlogpostDto.setId(blogPost.getId());
            outputBlogpostDto.setCategories(blogPost.getCategories());
            outputBlogpostDto.setDate(blogPost.getDate());
            outputBlogpostDtoList.add(outputBlogpostDto);
        }
        ;
        return outputBlogpostDtoList;
    }

    public void updateBlogPost(Long id, InputBlogpostDto blogPostDto) throws IOException {
        // Fetch the BlogPost entity from the database
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ExceptionController.ResourceNotFoundException("Blog post not found"));

        if (blogPostDto.getFile() != null) {
            blogPost.setImageData(ImageUtil.compressImage(blogPostDto.getFile().getBytes()));
        }

        blogPost.setCaption(blogPostDto.getCaption());
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setSubtitle(blogPostDto.getSubtitle());
        blogPost.setContent(blogPostDto.getContent());

        User user = userRepository.findById(blogPostDto.getUsername())
                .orElseThrow(() -> new ExceptionController.ResourceNotFoundException("User not found"));
        blogPost.setUser(user);
        blogPost.setDate(blogPostDto.getDate());
        blogPost.setCategories(blogPostDto.getCategories());
        blogPostRepository.save(blogPost);
    }


    @Transactional
    public List<OutputBlogpostDto> getBlogPostByUsername(String username) {
        List<BlogPost> blogPostList = blogPostRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityNotFoundException("Blog post not found with username " + username));

        List<OutputBlogpostDto> outputBlogpostDtoList = new ArrayList<>();

        for (BlogPost blogPost : blogPostList) {
            OutputBlogpostDto outputBlogpostDto = new OutputBlogpostDto();
            outputBlogpostDto.setTitle(blogPost.getTitle());
            outputBlogpostDto.setSubtitle(blogPost.getSubtitle());
            outputBlogpostDto.setCaption(blogPost.getCaption());
            outputBlogpostDto.setContent(blogPost.getContent());
            outputBlogpostDto.setUsername(blogPost.getUser().getUsername());
            outputBlogpostDto.setId(blogPost.getId());
            outputBlogpostDto.setFileContent(ImageUtil.decompressImage(blogPost.getImageData()));
            outputBlogpostDto.setCategories(blogPost.getCategories());
            outputBlogpostDto.setDate(blogPost.getDate());
            outputBlogpostDtoList.add(outputBlogpostDto);
        }
        return outputBlogpostDtoList;
    }

    private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    public void deleteBlogPost(String username, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Blog post not found"));
        User user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            // kijkt of ROLE_ADMIN is en doet verwijderen
            blogPostRepository.delete(blogPost);
        } else if (blogPost.getUser().getUsername().equals(username)) {
            blogPostRepository.delete(blogPost);
        } else {
            // denied
            throw new AccessDeniedException("You are not allowed to delete this blog post");
        }
    }
}

