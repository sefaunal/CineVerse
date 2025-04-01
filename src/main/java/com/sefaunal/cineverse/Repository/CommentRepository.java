package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Comment;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author github.com/sefaunal
 * @since 2025-04-01
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByMovieID(String movieID);
}
