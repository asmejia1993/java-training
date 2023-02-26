package com.asmejia93.articles.service;

import static com.asmejia93.articles.constants.ArticleType.CASE_STUDY;
import static com.asmejia93.articles.constants.ArticleType.EMPIRICAL_STUDY;
import static com.asmejia93.articles.constants.ArticleType.LITERATURE_REVIEW;
import static com.asmejia93.articles.constants.ArticleType.METHODOLOGIC;
import static com.asmejia93.articles.constants.ArticleType.THEORIC;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.asmejia93.articles.exception.NotFoundException;
import com.asmejia93.articles.model.Article;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTestCandidate {

    @Mock
    private ArticleService articleService;

    @Captor
    private ArgumentCaptor<Article> articleArgumentCaptor;

    private List<Article> articles;

    @Before
    public void setUp() {
        Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Article article3 = new Article(3, randomAlphabetic(10), randomAlphabetic(200), LITERATURE_REVIEW,
                newArrayList());
        Article article4 = new Article(4, randomAlphabetic(10), randomAlphabetic(200), METHODOLOGIC, newArrayList());
        Article article5 = new Article(5, randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
        Article article6 = new Article(6, randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
        articles = newArrayList(article1, article2, article3, article4, article5, article6);
        ArticleService.articles = articles;
    }

    @Test
    public void shouldFindAllArticles() {
        when(articleService.findAll()).thenReturn(articles);

        List<Article> articlesResultList = articleService.findAll();

        assertEquals(articles.size(), articlesResultList.size());
    }

    @Test
    public void shouldFindAnArticleById() {
        when(articleService.findById(1)).thenReturn(articles.get(0));

        Article article = articleService.findById(1);
        
        verify(articleService, times(1)).findById(1);

        assertEquals(articles.get(0), article);
    }

    @Test
    public void shouldReturnArticleNotFoundExceptionWhenNotExist() {
        when(articleService.findById(110)).thenThrow(new NotFoundException("Article not found"));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            articleService.findById(110);
        });
        assertTrue(exception.getMessage().contains("Article not found"));
    }

    @Test
    public void shouldSaveAnArticle() {
        when(articleService.save(articles.get(0))).thenReturn(articles.get(0));

        Article article = articleService.save(articles.get(0));

        verify(articleService, times(1)).save(articles.get(0));
        verify(articleService).save(articleArgumentCaptor.capture());

        Article articleCreated = articleArgumentCaptor.getValue();

        assertNotNull(articleCreated.getId());
        assertEquals(articles.get(0), article);
    }

    @Test
    public void shouldDeleteAnArticle() {

        int sizeBeforeToDelete = ArticleService.articles.size();

        articleService.delete(articles.get(0).getId());
        ArgumentCaptor<Integer> articleArgument = ArgumentCaptor.forClass(Integer.class);

        verify(articleService, times(1)).delete(articles.get(0).getId());
        verify(articleService).delete(articleArgument.capture());

        Integer articleDeleted = articleArgument.getValue();

        assertNotNull(articleDeleted);
        assertEquals(articles.size(), sizeBeforeToDelete);
    }
}
