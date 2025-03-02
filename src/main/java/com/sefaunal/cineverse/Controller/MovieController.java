package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Actor;
import com.sefaunal.cineverse.Model.Genre;
import com.sefaunal.cineverse.Model.Language;
import com.sefaunal.cineverse.Model.Movie;
import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Service.ActorService;
import com.sefaunal.cineverse.Service.GenreService;
import com.sefaunal.cineverse.Service.LanguageService;
import com.sefaunal.cineverse.Service.MovieService;
import com.sefaunal.cineverse.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

/**
 * @author github.com/sefaunal
 * @since 2025-02-22
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/content/movie")
public class MovieController {
    private final MovieService movieService;
    private final ActorService actorService;
    private final GenreService genreService;
    private final LanguageService languageService;
    private final UserService userService;

    @PostMapping("/save")
    public RedirectView saveMovie(@ModelAttribute Movie movie, @RequestParam MultipartFile movieImage) {
        return movieService.saveMovie(movie, movieImage);
    }

    @GetMapping("/update")
    public ModelAndView updateMoviePage(@RequestParam String ID, Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Movie movie = movieService.findRecordByID(ID);

        model.addAttribute("user", user);
        model.addAttribute("movie", movie);
        model.addAttribute("updateType", "MOVIE");

        List<Actor> actors = actorService.findAll();
        model.addAttribute("actors", actors);

        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        List<Language> languages = languageService.findAll();
        model.addAttribute("languages", languages);

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("/update")
    public RedirectView updateMovieRecord(@ModelAttribute Movie movie, @RequestParam MultipartFile movieImage,
                                          @RequestParam String password, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        return movieService.updateRecordByID(movie, movieImage, user.getPassword(), password);
    }
}
