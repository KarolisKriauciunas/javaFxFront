package com.example.kursinis.model.Forumas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Comment {
    private Long commentid;
    private Long replyid;
    private String content;
    private Long forumid;

    @Override
    public String toString() {
        return  content;
    }
}
