package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2025-02-22
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    Optional<Movie> findByMovieName(String movieName);

    Optional<Movie> findByID(String ID);

    @Query("{ 'movieActors': { $in: [?0] } }")
    Page<Movie> findAllByActorID(String actorId, Pageable pageable);

    @Query("{ 'movieGenres': { $in: [?0] } }")
    Page<Movie> findAllByGenreID(String actorId, Pageable pageable);

    @Query("{ 'movieName': { $regex: ?0, $options: 'i' } }")
    List<Movie> findAllByMovieName(String searchParam);
}