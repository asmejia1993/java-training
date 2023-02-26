package com.asmejia93.articles.service;

import static com.asmejia93.articles.constants.ArticleType.CASE_STUDY;
import static com.asmejia93.articles.constants.ArticleType.EMPIRICAL_STUDY;
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
import com.asmejia93.articles.model.Comment;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTestCandidate {

    @Mock
    private ArticleService articleService;
    @Mock
    private CommentService commentService;

    @Captor
    private ArgumentCaptor<Comment> commentArgumentCaptor;

    private List<Comment> comments;
    private List<Article> articles;

    @Before
    public void setUp() {
        Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Comment comment1 = new Comment(1, article1, "test@gmail.com", randomAlphabetic(100));
        Comment comment2 = new Comment(2, article1, "test2@gmail.com", randomAlphabetic(100));
        Comment comment3 = new Comment(3, article1, "test3@gmail.com", randomAlphabetic(100));

        Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Comment comment4 = new Comment(4, article2, "test4@gmail.com", randomAlphabetic(100));
        Comment comment5 = new Comment(5, article2, "test5@gmail.com", randomAlphabetic(100));
        Comment comment6 = new Comment(6, article2, "test6@gmail.com", randomAlphabetic(100));

        comments = newArrayList(comment1, comment2, comment3, comment4, comment5, comment6);
        articles = newArrayList(article1, article2);

        ArticleService.articles = articles;
        CommentService.comments = comments;
    }

    @Test
    public void shouldFindAllComments() {
        when(commentService.findAll()).thenReturn(comments);

        List<Comment> commentsResultList = commentService.findAll();

        assertEquals(comments.size(), commentsResultList.size());
    }

    @Test
    public void shouldFindCommentById() {
        when(commentService.findById(1)).thenReturn(comments.get(0));

        Comment comment = commentService.findById(1);

        assertEquals(comments.get(0), comment);
    }

    @Test
    public void shouldReturnArticleNotFoundExceptionWhenArticleIdNotExist() {
        when(commentService.findById(110)).thenThrow(new NotFoundException("Comment not found"));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            commentService.findById(110);
        });
        assertTrue(exception.getMessage().contains("Comment not found"));
    }

    @Test
    public void shouldSaveComment() {
        when(articleService.findById(1)).thenReturn(articles.get(0));
        when(commentService.save(commentArgumentCaptor.capture())).thenReturn(comments.get(0));

        Comment comment = new Comment(1, articles.get(0), "test@gmail.com", randomAlphabetic(100));

        Comment savedComment = commentService.save(comment);

        assertEquals(comments.get(0), savedComment);
        assertEquals(1, commentArgumentCaptor.getValue().getId());
    }

    @Test
    public void shouldDeleteAComment() {
        int sizeBeforeToDelete = CommentService.comments.size();

        commentService.delete(comments.get(0).getId());
        ArgumentCaptor<Integer> commentArgument = ArgumentCaptor.forClass(Integer.class);

        verify(commentService, times(1)).delete(comments.get(0).getId());
        verify(commentService).delete(commentArgument.capture());

        Integer commentDeleted = commentArgument.getValue();

        assertNotNull(commentDeleted);
        assertEquals(comments.size(), sizeBeforeToDelete);
    }
}
