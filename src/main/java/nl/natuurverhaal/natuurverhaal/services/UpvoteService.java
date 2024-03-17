package nl.natuurverhaal.natuurverhaal.services;

import lombok.Data;
import nl.natuurverhaal.natuurverhaal.controllers.ExceptionController;
import nl.natuurverhaal.natuurverhaal.dtos.InputUpvoteDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputUpvoteDto;
import nl.natuurverhaal.natuurverhaal.exceptions.UsernameNotFoundException;
import nl.natuurverhaal.natuurverhaal.models.BlogPost;
import nl.natuurverhaal.natuurverhaal.models.Upvote;
import nl.natuurverhaal.natuurverhaal.models.User;
import nl.natuurverhaal.natuurverhaal.repositories.BlogPostRepository;
import nl.natuurverhaal.natuurverhaal.repositories.UpvoteRepository;
import nl.natuurverhaal.natuurverhaal.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Data
@Service
public class UpvoteService {
    private final UpvoteRepository upvoteRepository;
    private final UserRepository userRepository;
    private final BlogPostRepository blogPostRepository;

    public OutputUpvoteDto upvoteBlogPost(InputUpvoteDto inputUpvoteDto) {
        User user = userRepository.findByUsername(inputUpvoteDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        BlogPost blogPost = blogPostRepository.findById(inputUpvoteDto.getBlogPostId())
                .orElseThrow(() -> new ExceptionController.ResourceNotFoundException("Blog post not found"));

        if (upvoteRepository.findByUserAndBlogPost(user, blogPost).isPresent()) {
            throw new ExceptionController.AlreadyExistsException("User has already upvoted this blog post");
        }

        Upvote upvote = new Upvote();
        upvote.setUser(user);
        upvote.setBlogPost(blogPost);
        upvote = upvoteRepository.save(upvote);

        blogPost.incrementUpvotes();
        blogPostRepository.save(blogPost);

        OutputUpvoteDto outputUpvoteDto = new OutputUpvoteDto();
        outputUpvoteDto.setId(upvote.getId());
        outputUpvoteDto.setUsername(user.getUsername());
        outputUpvoteDto.setBlogPostId(blogPost.getId());

        return outputUpvoteDto;
    }



}