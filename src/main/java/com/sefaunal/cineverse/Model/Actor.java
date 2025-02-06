package com.sefaunal.cineverse.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2025-01-28
 */
@Data
public class Actor {
    @Id
    private String ID;

    private String actorName;

    private String actorGender;

    private Instant creationDate;
}
