package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Actor;
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
public interface ActorRepository extends MongoRepository<Actor, String> {
    Optional<Actor> findByID(String ID);

    Optional<Actor> findByActorName(String actorName);

    @Query("{ 'actorName': { $regex: ?0, $options: 'i' } }")
    List<Actor> findAllByActorName(String searchParam);

    @Query("{ '_id': { $in: ?0 } }")
    List<Actor> findAllByIdIn(List<String> actorIds);
}