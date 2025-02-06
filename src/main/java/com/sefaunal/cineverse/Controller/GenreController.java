package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Genre;
import com.sefaunal.cineverse.Repository.GenreRepository;
import com.sefaunal.cineverse.Service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/content/genre")
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/save")
    public RedirectView saveGenre(@ModelAttribute Genre genre) {
        genreService.saveGenre(genre);

        return new RedirectView("/content/panel/genres");
    }
}