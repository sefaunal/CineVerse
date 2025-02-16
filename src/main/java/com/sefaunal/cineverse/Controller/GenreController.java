package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Genre;
import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Service.GenreService;
import com.sefaunal.cineverse.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/content/genre")
public class GenreController {
    private final GenreService genreService;
    private final UserService userService;

    @PostMapping("/save")
    public RedirectView saveGenre(@ModelAttribute Genre genre) {
        return genreService.saveGenre(genre);
    }

    @GetMapping("/update")
    public ModelAndView updateGenrePage(@RequestParam String ID, Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Genre genre = genreService.findRecordByID(ID);

        model.addAttribute("user", user);
        model.addAttribute("genre", genre);
        model.addAttribute("updateType", "GENRE");

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("update")
    public RedirectView updateGenreRecord(@ModelAttribute Genre genre, @RequestParam String password, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        return genreService.updateRecordByID(genre, user.getPassword(), password);
    }
}