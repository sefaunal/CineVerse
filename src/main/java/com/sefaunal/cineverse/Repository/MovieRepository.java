package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2025-02-22
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    Optional<Movie> findByMovieName(String movieName);

    Optional<Movie> findByID(String ID);
}