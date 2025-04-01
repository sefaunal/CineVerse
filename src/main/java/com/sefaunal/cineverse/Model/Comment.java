package com.sefaunal.cineverse.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2025-04-01
 */
@Data
@Document
public class Comment {
    @Id
    private String commentID;
    private String context;
    private Instant commentDate;
    private String movieID;
    private String userID;
    private User userDetails;
}
