package com.sefaunal.cineverse.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author github.com/sefaunal
 * @since 2025-02-22
 */

@Data
@Document
public class Movie {
    @Id
    private String ID;

    private String movieName;

    private String movieDescription;

    private Date movieReleaseDate;

    private String movieRating;

    private String movieTrailer;

    private String movieImageURI;

    private List<String> movieLanguages;

    private List<String> movieActors;

    private List<String> movieGenres;

    private Instant creationDate;
}
