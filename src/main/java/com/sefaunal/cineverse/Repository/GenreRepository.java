package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByID(String ID);

    Optional<Genre> findByGenreName(String genreName);

    @Query("{ '_id': { $in: ?0 } }")
    List<Genre> findAllByIdIn(List<String> genreIds);
}