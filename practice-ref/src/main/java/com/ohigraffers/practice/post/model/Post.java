package com.ohigraffers.practice.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class Post {

    private Long code;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void modifyTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }
}
