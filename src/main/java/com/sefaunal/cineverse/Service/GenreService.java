package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Genre;
import com.sefaunal.cineverse.Repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public void saveGenre(Genre genre) {
        genre.setCreationDate(Instant.now());
        genreRepository.save(genre);
    }

    public Page<Genre> findAllWithPageable(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }
}