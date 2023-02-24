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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.asmejia93.articles.model.Article;
import com.asmejia93.articles.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTestCandidate {

    private List<Article> articles;
    private Article article1;
    private Article article2;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    // Constants
    private final Integer articleId = 1;

    @Before
    public void setUp() {
        articles = new ArrayList<Article>();
        article1 = new Article(1, "test1", "test2", CASE_STUDY, newArrayList());
        article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        articles = newArrayList(article1, article2);
        ArticleService.articles = articles;
    }

    @Test
    public void shouldRetrieveAllArticles() throws Exception {

        when(articleService.findAll()).thenReturn(articles);

        this.mockMvc.perform(get("/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(articles.size())));
    }

    @Test
    public void shouldRetrieveAnArticleById() throws Exception {

        when(articleService.findById(articleId)).thenReturn(article1);

        this.mockMvc.perform(get("/articles/{id}", articleId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(article1.getTitle())))
                .andExpect(jsonPath("$.body", is(article1.getBody())));
    }

    @Test
    public void shouldCreateAnArticle() throws Exception {
        Article mockArticle = new Article(10, "mock article", "mock body", CASE_STUDY);

        when(articleService.save(any(Article.class))).thenReturn(mockArticle);

        String request = objectMapper.writeValueAsString(mockArticle);

        this.mockMvc.perform(
                post("/articles")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(mockArticle.getId())))
                .andExpect(jsonPath("$.title", is(mockArticle.getTitle())))
                .andReturn();
    }

    @Test
    public void shouldUpdateAnArticle() throws Exception {
        Article mockArticle = new Article(10, "mock article", "mock body", CASE_STUDY);

        when(articleService.update(Mockito.anyInt(), any(Article.class))).thenReturn(mockArticle);

        String request = objectMapper.writeValueAsString(mockArticle);

        this.mockMvc.perform(
                put("/articles/{id}", articleId)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockArticle.getId())))
                .andExpect(jsonPath("$.title", is(mockArticle.getTitle())))
                .andReturn();
    }

    @Test
    public void shouldDeleteAnArticle() throws Exception {
        when(articleService.findById(articleId)).thenReturn(article1);

        doNothing().when(articleService).delete(anyInt());

        this.mockMvc.perform(delete("/articles/{id}", articleId).contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
    }

}
