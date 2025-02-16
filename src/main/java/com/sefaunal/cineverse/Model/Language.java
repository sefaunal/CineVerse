package com.sefaunal.cineverse.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2025-01-28
 */
@Data
public class Language {
    @Id
    private String ID;

    private String languageName;

    private Instant creationDate;
}