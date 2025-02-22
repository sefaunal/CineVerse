package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Actor;
import com.sefaunal.cineverse.Repository.ActorRepository;
import com.sefaunal.cineverse.Utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.List;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;

    public RedirectView saveActor(Actor actor) {
        if (actorRepository.findByActorName(actor.getActorName()).isPresent()) {
            return new RedirectView("/content/panel/actors/recordAlreadyExists");
        }

        actor.setCreationDate(Instant.now());
        actorRepository.save(actor);
        return new RedirectView("/content/panel/actors/additionSuccess");
    }

    public Page<Actor> findAllWithPageable(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Actor findRecordByID(String ID) {
        return actorRepository.findByID(ID).orElseThrow();
    }

    public RedirectView updateRecordByID(Actor actor, String hashedPassword, String rawPassword) {
        Actor actorUpdated = actorRepository.findByID(actor.getID()).orElseThrow();

        if (!actor.getActorName().equals(actorUpdated.getActorName()) && actorRepository.findByActorName(actor.getActorName()).isPresent()) {
            return new RedirectView("/content/panel/actors/recordAlreadyExists");
        }

        if (!CommonUtils.checkPasswordsMatch(rawPassword, hashedPassword)) {
            return new RedirectView("/content/panel/actors/passwordsDoNotMatch");
        }

        actorUpdated.setActorName(actor.getActorName());
        actorUpdated.setActorGender(actor.getActorGender());

        actorRepository.save(actorUpdated);
        return new RedirectView("/content/panel/actors/updateSuccess");
    }
}