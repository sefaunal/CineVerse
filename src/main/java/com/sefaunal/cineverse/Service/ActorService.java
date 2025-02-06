package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Actor;
import com.sefaunal.cineverse.Repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;

    public void saveActor(Actor actor) {
        actor.setCreationDate(Instant.now());
        actorRepository.save(actor);
    }

    public Page<Actor> findAllWithPageable(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }
}