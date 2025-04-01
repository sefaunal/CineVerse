package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Language;
import com.sefaunal.cineverse.Repository.LanguageRepository;
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
public class LanguageService {
    private final LanguageRepository languageRepository;

    public RedirectView saveLanguage(Language language) {
        if (languageRepository.findByLanguageName(language.getLanguageName()).isPresent()) {
            return new RedirectView("/content/panel/languages/recordAlreadyExists");
        }

        language.setCreationDate(Instant.now());
        languageRepository.save(language);
        return new RedirectView("/content/panel/languages/additionSuccess");
    }

    public Page<Language> findAllWithPageable(Pageable pageable) {
        return languageRepository.findAll(pageable);
    }

    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    public Language findRecordByID(String ID) {
        return languageRepository.findByID(ID).orElseThrow();
    }

    public List<Language> findAllByIdIn(List<String> languageIds) {
        return languageRepository.findAllByIdIn(languageIds);
    }

    public RedirectView updateRecordByID(Language language, String hashedPassword, String rawPassword) {
        Language languageUpdated = languageRepository.findByID(language.getID()).orElseThrow();

        if (!language.getLanguageName().equals(languageUpdated.getLanguageName()) && languageRepository.findByLanguageName(language.getLanguageName()).isPresent()) {
            return new RedirectView("/content/panel/languages/recordAlreadyExists");
        }

        if (!CommonUtils.checkPasswordsMatch(rawPassword, hashedPassword)) {
            return new RedirectView("/content/panel/languages/passwordsDoNotMatch");
        }

        languageUpdated.setLanguageName(language.getLanguageName());

        languageRepository.save(languageUpdated);
        return new RedirectView("/content/panel/languages/updateSuccess");
    }
}