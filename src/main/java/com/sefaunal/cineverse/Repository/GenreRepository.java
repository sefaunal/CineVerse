package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
}