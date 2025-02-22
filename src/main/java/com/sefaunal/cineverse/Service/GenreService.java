package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Genre;
import com.sefaunal.cineverse.Repository.GenreRepository;
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
public class GenreService {
    private final GenreRepository genreRepository;

    public RedirectView saveGenre(Genre genre) {
        if (genreRepository.findByGenreName(genre.getGenreName()).isPresent()) {
            return new RedirectView("/content/panel/genres/recordAlreadyExists");
        }

        genre.setCreationDate(Instant.now());
        genreRepository.save(genre);
        return new RedirectView("/content/panel/genres/additionSuccess");
    }

    public Page<Genre> findAllWithPageable(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre findRecordByID(String ID) {
        return genreRepository.findByID(ID).orElseThrow();
    }

    public RedirectView updateRecordByID(Genre genre, String hashedPassword, String rawPassword) {
        Genre genreUpdated = genreRepository.findByID(genre.getID()).orElseThrow();

        if (!genre.getGenreName().equals(genreUpdated.getGenreName()) && genreRepository.findByGenreName(genre.getGenreName()).isPresent()) {
            return new RedirectView("/content/panel/genres/recordAlreadyExists");
        }

        if (!CommonUtils.checkPasswordsMatch(rawPassword, hashedPassword)) {
            return new RedirectView("/content/panel/genres/passwordsDoNotMatch");
        }

        genreUpdated.setGenreName(genre.getGenreName());

        genreRepository.save(genreUpdated);
        return new RedirectView("/content/panel/genres/updateSuccess");
    }
}