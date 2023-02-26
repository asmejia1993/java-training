package com.asmejia93.articles.controller;

import static com.asmejia93.articles.constants.ArticleType.CASE_STUDY;
import static com.asmejia93.articles.constants.ArticleType.EMPIRICAL_STUDY;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.asmejia93.articles.dto.CommentDto;
import com.asmejia93.articles.model.Article;
import com.asmejia93.articles.model.Comment;
import com.asmejia93.articles.service.ArticleService;
import com.asmejia93.articles.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTestCandidate {
        private List<Comment> comments;
        private List<Article> articles;

        private final Integer articleId = 1;
        private final Integer commentId = 1;
        private Article mockArticle;
        private Comment mockComment;

        private static final String CANNOT_EMPTY_OR_NULL = "Cannot be null/empty";
        private static final String CANNOT_NULL = "Cannot be null";
        private static final String EMAIL_VALIDATION_MESSAGE = "The email must be valid";

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private ArticleService articleService;

        @MockBean
        private CommentService commentService;

        @Autowired
        private MockMvc mockMvc;

        @Before
        public void setUp() {
                Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY,
                                newArrayList());
                Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY,
                                newArrayList());

                Comment comment1 = new Comment(1, article1, "test@gmail.com", randomAlphabetic(100));
                Comment comment2 = new Comment(2, article1, "test2@gmail.com", randomAlphabetic(100));
                Comment comment3 = new Comment(3, article2, "test3@gmail.com", randomAlphabetic(100));

                comments = newArrayList(comment1, comment2, comment3);
                articles = newArrayList(article1, article2);
                ArticleService.articles = articles;
                CommentService.comments = comments;
        }

    @Test
    public void shouldRetrieveAllComments() throws Exception {
        when(commentService.findAll()).thenReturn(comments);

        this.mockMvc.perform(get("/comments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(comments.size())));
    }

        @Test
        public void shouldRetrieveACommentById() throws Exception {
                mockArticle = new Article(articleId, "TEST", "TEST", EMPIRICAL_STUDY);
                mockComment = new Comment(commentId, mockArticle, "test@gmail", "test m");

                when(articleService.findById(articleId)).thenReturn(mockArticle);
                when(commentService.findById(commentId)).thenReturn(mockComment);

                this.mockMvc.perform(get("/comments/{id}", commentId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email", is(mockComment.getEmail())))
                                .andExpect(jsonPath("$.article.id", is(mockComment.getArticle().getId())));
        }

        @Test
        public void shouldCreateAComment() throws Exception {
                Article mockArticle = new Article(articleId, "TEST", "TEST", EMPIRICAL_STUDY);
                Comment mockComment = new Comment(commentId, mockArticle, "test@gmail.com", "test m");

                when(articleService.findById(articleId)).thenReturn(mockArticle);
                when(commentService.save(any(Comment.class))).thenReturn(mockComment);

                CommentDto commentDto = new CommentDto(commentId, articleId, "test@gmail.com", "test m");
                String request = objectMapper.writeValueAsString(commentDto);

                this.mockMvc.perform(
                                post("/comments")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(request)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.email", is(mockComment.getEmail())))
                                .andExpect(jsonPath("$.article.id", is(mockComment.getArticle().getId())))
                                .andReturn();
        }

        @Test
        public void shouldNotCreateACommentWhenInputIsInvalid() throws Exception {
                Article mockArticle = new Article(articleId, "TEST", "TEST", EMPIRICAL_STUDY);
                Comment mockComment = new Comment(commentId, mockArticle, "test@gmail", "test m");

                when(articleService.findById(articleId)).thenReturn(mockArticle);
                when(commentService.save(any(Comment.class))).thenReturn(mockComment);

                CommentDto commentDto = new CommentDto(commentId, null, "@gmail", "");
                String request = objectMapper.writeValueAsString(commentDto);

                this.mockMvc.perform(
                                post("/comments")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(request)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.email", is(EMAIL_VALIDATION_MESSAGE)))
                                .andExpect(jsonPath("$.articleId", is(CANNOT_NULL)))
                                .andExpect(jsonPath("$.text", is(CANNOT_EMPTY_OR_NULL)))
                                .andReturn();
        }

        @Test
        public void shouldUpdateAComment() throws Exception {
                Article mockArticle = new Article(articleId, "TEST", "TEST", EMPIRICAL_STUDY);
                Comment mockComment = new Comment(commentId, mockArticle, "test@gmail.com", "test m");

                when(articleService.findById(articleId)).thenReturn(mockArticle);
                when(commentService.update(anyInt(), any(Comment.class))).thenReturn(mockComment);

                CommentDto commentDto = new CommentDto(commentId, articleId, "test@gmail.com", "test m");
                String request = objectMapper.writeValueAsString(commentDto);

                this.mockMvc.perform(
                                put("/comments/{id}", commentId)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(request)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email", is(mockComment.getEmail())))
                                .andExpect(jsonPath("$.article.id", is(mockComment.getArticle().getId())));
        }

        @Test
        public void shouldNotUpdateACommentWhenInputIsInvalid() throws Exception {
                Article mockArticle = new Article(articleId, "TEST", "TEST", EMPIRICAL_STUDY);
                Comment mockComment = new Comment(commentId, mockArticle, "test@gmail", "test m");

                when(articleService.findById(articleId)).thenReturn(mockArticle);
                when(commentService.update(anyInt(), any(Comment.class))).thenReturn(mockComment);

                CommentDto commentDto = new CommentDto(commentId, null, "@gmail", "");
                String request = objectMapper.writeValueAsString(commentDto);

                this.mockMvc.perform(
                                put("/comments/{id}", commentId)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(request)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.email", is(EMAIL_VALIDATION_MESSAGE)))
                                .andExpect(jsonPath("$.articleId", is(CANNOT_NULL)))
                                .andExpect(jsonPath("$.text", is(CANNOT_EMPTY_OR_NULL)))
                                .andReturn();
        }

    @Test
    public void shouldDeleteAComment() throws Exception {
        when(commentService.findById(commentId)).thenReturn(mockComment);

        doNothing().when(commentService).delete(anyInt());

        this.mockMvc.perform(delete("/comments/{id}", commentId).contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
    }
}
