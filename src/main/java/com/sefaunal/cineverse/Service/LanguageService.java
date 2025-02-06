package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Language;
import com.sefaunal.cineverse.Repository.LanguageRepository;
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
public class LanguageService {
    private final LanguageRepository languageRepository;

    public void saveLanguage(Language language) {
        language.setCreationDate(Instant.now());
        languageRepository.save(language);
    }

    public Page<Language> findAllWithPageable(Pageable pageable) {
        return languageRepository.findAll(pageable);
    }
}