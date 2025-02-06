package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Language;
import com.sefaunal.cineverse.Service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/content/language")
public class LanguageController {
    private final LanguageService languageService;

    @PostMapping("/save")
    public RedirectView saveLanguage(@ModelAttribute Language language) {
        languageService.saveLanguage(language);

        return new RedirectView("/content/panel/languages");
    }
}