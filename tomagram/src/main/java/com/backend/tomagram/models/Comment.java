package com.backend.tomagram.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    private long post_id;

    private String user_id;

    private String content;

    private LocalDateTime timestamp;
}
