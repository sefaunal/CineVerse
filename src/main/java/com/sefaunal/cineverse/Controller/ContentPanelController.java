package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Actor;
import com.sefaunal.cineverse.Model.Genre;
import com.sefaunal.cineverse.Model.Language;
import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Service.ActorService;
import com.sefaunal.cineverse.Service.GenreService;
import com.sefaunal.cineverse.Service.LanguageService;
import com.sefaunal.cineverse.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Controller
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentPanelController {
    private final ActorService actorService;

    private final GenreService genreService;

    private final LanguageService languageService;

    private final UserService userService;

    private static final Integer REQUEST_SIZE = 50;

    @GetMapping("/panel/actors")
    public ModelAndView contentPanelActorList(@RequestParam(defaultValue = "1") int page,
                                       Principal principal,
                                       Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Actor> actors = actorService.findAllWithPageable(pageable);

        model.addAttribute("actorList", actors.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actors.getTotalPages());

        model.addAttribute("user", user);
        return new ModelAndView("panel/ActorList");
    }

    @GetMapping("/panel/genres")
    public ModelAndView contentPanelGenreList(@RequestParam(defaultValue = "1") int page,
                                              Principal principal,
                                              Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Genre> genres = genreService.findAllWithPageable(pageable);

        model.addAttribute("genreList", genres.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", genres.getTotalPages());

        model.addAttribute("user", user);
        return new ModelAndView("panel/GenreList");
    }

    @GetMapping("/panel/languages")
    public ModelAndView contentPanelLanguageList(@RequestParam(defaultValue = "1") int page,
                                              Principal principal,
                                              Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Language> languages = languageService.findAllWithPageable(pageable);

        model.addAttribute("languageList", languages.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", languages.getTotalPages());

        model.addAttribute("user", user);
        return new ModelAndView("panel/LanguageList");
    }
}