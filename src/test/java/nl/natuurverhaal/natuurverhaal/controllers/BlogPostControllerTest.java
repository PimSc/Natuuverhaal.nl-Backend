package nl.natuurverhaal.natuurverhaal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.natuurverhaal.natuurverhaal.dtos.InputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.dtos.OutputBlogpostDto;
import nl.natuurverhaal.natuurverhaal.services.BlogPostService;
import nl.natuurverhaal.natuurverhaal.utils.Category;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogPostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogPostService blogPostService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBlogPost() throws Exception {
        OutputBlogpostDto blogPost = new OutputBlogpostDto();
        blogPost.setUsername("testUsername");
        when(blogPostService.getBlogPost(anyString(), any(Long.class))).thenReturn(blogPost);

        mockMvc.perform(get("/blog-posts/testUsername/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUsername"));
    }

    @Test
    void getBlogPostByUsername() throws Exception {
        OutputBlogpostDto blogPost = new OutputBlogpostDto();
        blogPost.setUsername("testUsername");
        when(blogPostService.getBlogPostByUsername(anyString())).thenReturn(Collections.singletonList(blogPost));

        mockMvc.perform(get("/blog-posts/testUsername"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("testUsername"));
    }

    @Test
    void getAllBlogs() throws Exception {
        OutputBlogpostDto blogPost = new OutputBlogpostDto();
        blogPost.setUsername("testUsername");
        when(blogPostService.getAllBlogs()).thenReturn(Collections.singletonList(blogPost));

        mockMvc.perform(get("/blog-posts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("testUsername"));
    }

    @Test
    void createBlogPost() throws Exception {
        InputBlogpostDto blogPost = new InputBlogpostDto();
        blogPost.setUsername("testUsername");
        blogPost.setCaption("testCaption");
        blogPost.setTitle("testTitle");
        blogPost.setSubtitle("testSubtitle");
        blogPost.setContent("testContent");
        blogPost.setCategories(new HashSet<>(Collections.singletonList(Category.sport)));

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes());
        MockMultipartFile usernamePart = new MockMultipartFile("username", "", "text/plain", "testUsername".getBytes());
        MockMultipartFile captionPart = new MockMultipartFile("caption", "", "text/plain", "testCaption".getBytes());
        MockMultipartFile titlePart = new MockMultipartFile("title", "", "text/plain", "testTitle".getBytes());
        MockMultipartFile subtitlePart = new MockMultipartFile("subtitle", "", "text/plain", "testSubtitle".getBytes());
        MockMultipartFile contentPart = new MockMultipartFile("content", "", "text/plain", "testContent".getBytes());
        MockMultipartFile categoriesPart = new MockMultipartFile("categories", "", "text/plain", "sport".getBytes());

        mockMvc.perform(multipart("/blog-posts/testUsername")
                        .file(file)
                        .file(usernamePart)
                        .file(captionPart)
                        .file(titlePart)
                        .file(subtitlePart)
                        .file(contentPart)
                        .file(categoriesPart))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBlogPost() throws Exception {
        InputBlogpostDto blogPost = new InputBlogpostDto();
        blogPost.setUsername("testUsername");
        blogPost.setCaption("testCaption");
        blogPost.setTitle("testTitle");
        blogPost.setSubtitle("testSubtitle");
        blogPost.setContent("testContent");
        blogPost.setCategories(new HashSet<>(Collections.singletonList(Category.sport)));

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes());
        MockMultipartFile usernamePart = new MockMultipartFile("username", "", "text/plain", "testUsername".getBytes());
        MockMultipartFile captionPart = new MockMultipartFile("caption", "", "text/plain", "testCaption".getBytes());
        MockMultipartFile titlePart = new MockMultipartFile("title", "", "text/plain", "testTitle".getBytes());
        MockMultipartFile subtitlePart = new MockMultipartFile("subtitle", "", "text/plain", "testSubtitle".getBytes());
        MockMultipartFile contentPart = new MockMultipartFile("content", "", "text/plain", "testContent".getBytes());
        MockMultipartFile categoriesPart = new MockMultipartFile("categories", "", "text/plain", "sport".getBytes());

        mockMvc.perform(multipart("/blog-posts/1")
                        .file(file)
                        .file(usernamePart)
                        .file(captionPart)
                        .file(titlePart)
                        .file(subtitlePart)
                        .file(contentPart)
                        .file(categoriesPart)
                        .with(putMultipart()))
                .andExpect(status().isNoContent());
    }

    private static RequestPostProcessor putMultipart() {
        return request -> {
            request.setMethod("PUT");
            return request;
        };
    }

    @Test
    void deleteBlogPost() throws Exception {
        mockMvc.perform(delete("/blog-posts/testUsername/1"))
                .andExpect(status().isNoContent());
    }
}