package com.asmejia93.articles.dto;

import javax.validation.constraints.NotEmpty;

import com.asmejia93.articles.constants.ArticleType;

public class ArticleDto {
        private int id;
        @NotEmpty(message = "Cannot be null/empty")
        private String title;
        @NotEmpty(message = "Cannot be null/empty")
        private String body;
        private ArticleType type;

        public ArticleDto() {
        }

        public ArticleDto(int id, String title, String body, ArticleType type) {
                this.id = id;
                this.title = title;
                this.body = body;
                this.type = type;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getBody() {
                return body;
        }

        public void setBody(String body) {
                this.body = body;
        }

        public ArticleType getType() {
                return type;
        }

        public void setType(ArticleType type) {
                this.type = type;
        }

}
