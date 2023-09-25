package com.example.kursinis.model.Forumas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Forum {
    @JsonProperty(namespace = "forumID")
    private Long forumID;
    @JsonProperty(namespace = "forumName")
    private String forumName;
    @JsonProperty(namespace = "access")
    private Boolean access;
}
