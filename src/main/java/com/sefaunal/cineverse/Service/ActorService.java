package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Actor;

import com.sefaunal.cineverse.Repository.ActorRepository;
import com.sefaunal.cineverse.Utils.CommonUtils;
import com.sefaunal.cineverse.Utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;

    public RedirectView saveActor(Actor actor, MultipartFile actorImage) {
        if (actorRepository.findByActorName(actor.getActorName()).isPresent()) {
            return new RedirectView("/content/panel/actors/recordAlreadyExists");
        }

        String uniqueFileName = ImageUtils.generateUniqueFilename(
                actor.getActorName(),
                Objects.requireNonNull(actorImage.getContentType())
        );
        String imageURI = ImageUtils.uploadImageToFirebase(actorImage, uniqueFileName);

        actor.setCreationDate(Instant.now());
        actor.setActorImageURI(imageURI);

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

    public List<Actor> findAllByIdIn(List<String> actorIds) {
        return actorRepository.findAllByIdIn(actorIds);
    }

    public Page<Actor> searchActors(String searchParam, Pageable pageable) {
        List<Actor> potentialMatches = actorRepository.findAllByActorName(searchParam);

        // Filter actors where searchParam is at least 40% of the actor name
        List<Actor> filteredActors = potentialMatches.stream()
                .filter(actor -> {
                    String actorName = actor.getActorName().toLowerCase();
                    int requiredLength = (int) Math.ceil(actorName.length() * 0.4); // 40% of actor name
                    return searchParam.length() >= requiredLength;
                })
                .collect(Collectors.toList());

        // Apply pagination manually
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredActors.size());
        List<Actor> paginatedActors = filteredActors.subList(start, end);

        return new PageImpl<>(paginatedActors, pageable, filteredActors.size());
    }

    public RedirectView updateRecordByID(Actor actor, MultipartFile actorImage, String hashedPassword, String rawPassword) {
        if (!CommonUtils.checkPasswordsMatch(rawPassword, hashedPassword)) {
            return new RedirectView("/content/panel/actors/passwordsDoNotMatch");
        }

        if (!findRecordByID(actor.getID()).getActorName().equals(actor.getActorName())
                && actorRepository.findByActorName(actor.getActorName()).isPresent()) {
            return new RedirectView("/content/panel/actors/recordAlreadyExists");
        }

        if (!actorImage.isEmpty()) {
            String uniqueFileName = ImageUtils.generateUniqueFilename(actor.getActorName(), Objects.requireNonNull(actorImage.getContentType()));
            String newImageURI = ImageUtils.uploadImageToFirebase(actorImage, uniqueFileName);
            actor.setActorImageURI(newImageURI);
        }

        actor.setActorName(actor.getActorName());
        actor.setActorGender(actor.getActorGender());

        actorRepository.save(actor);
        return new RedirectView("/content/panel/actors/updateSuccess");
    }
}