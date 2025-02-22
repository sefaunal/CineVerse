package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Language;
import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Service.LanguageService;
import com.sefaunal.cineverse.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/content/language")
public class LanguageController {
    private final LanguageService languageService;
    private final UserService userService;

    @PostMapping("/save")
    public RedirectView saveLanguage(@ModelAttribute Language language) {
        return languageService.saveLanguage(language);
    }

    @GetMapping("/update")
    public ModelAndView updateGenrePage(@RequestParam String ID, Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Language language = languageService.findRecordByID(ID);

        model.addAttribute("user", user);
        model.addAttribute("language", language);
        model.addAttribute("updateType", "LANGUAGE");

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("update")
    public RedirectView updateActorRecord(@ModelAttribute Language language, @RequestParam String password, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        return languageService.updateRecordByID(language, user.getPassword(), password);
    }
}