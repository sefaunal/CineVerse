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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

    private final MovieService movieService;

    private final UserService userService;

    private static final Integer REQUEST_SIZE = 50;

    @GetMapping("/panel/actors")
    public RedirectView redirectToActorsPage() {
        return new RedirectView("/content/panel/actors/none");
    }

    @GetMapping("/panel/genres")
    public RedirectView redirectToGenresPage() {
        return new RedirectView("/content/panel/genres/none");
    }

    @GetMapping("/panel/languages")
    public RedirectView redirectToLanguagesPage() {
        return new RedirectView("/content/panel/languages/none");
    }

    @GetMapping("/panel/movies")
    public RedirectView redirectToMoviesPage() {
        return new RedirectView("/content/panel/movies/none");
    }

    @GetMapping("/panel/actors/{status}")
    public ModelAndView contentPanelActorList(@PathVariable String status, @RequestParam(defaultValue = "1") int page,
                                       Principal principal,
                                       Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Actor> actors = actorService.findAllWithPageable(pageable);

        model.addAttribute("actorList", actors.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actors.getTotalPages());

        model.addAttribute("user", user);

        model.addAttribute("status", status);
        return new ModelAndView("panel/ActorList");
    }

    @GetMapping("/panel/genres/{status}")
    public ModelAndView contentPanelGenreList(@PathVariable String status, @RequestParam(defaultValue = "1") int page,
                                              Principal principal,
                                              Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Genre> genres = genreService.findAllWithPageable(pageable);

        model.addAttribute("genreList", genres.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", genres.getTotalPages());

        model.addAttribute("user", user);

        model.addAttribute("status", status);
        return new ModelAndView("panel/GenreList");
    }

    @GetMapping("/panel/languages/{status}")
    public ModelAndView contentPanelLanguageList(@PathVariable String status, @RequestParam(defaultValue = "1") int page,
                                              Principal principal,
                                              Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Language> languages = languageService.findAllWithPageable(pageable);

        model.addAttribute("languageList", languages.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", languages.getTotalPages());

        model.addAttribute("user", user);

        model.addAttribute("status", status);
        return new ModelAndView("panel/LanguageList");
    }

    @GetMapping("/panel/movies/{status}")
    public ModelAndView contentPanelMovieList(@PathVariable String status, @RequestParam(defaultValue = "1") int page,
                                                 Principal principal,
                                                 Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Movie> movies = movieService.findAllWithPageable(pageable);

        List<Movie> updatedMovies = movies.getContent();
        updatedMovies.forEach(movie -> {
            movie.setMovieActors(movie.getMovieActors().stream()
                    .map(actorService::findRecordByID)
                    .map(Actor::getActorName)
                    .collect(Collectors.toList()));

            movie.setMovieLanguages(movie.getMovieLanguages().stream()
                    .map(languageService::findRecordByID)
                    .map(Language::getLanguageName)
                    .collect(Collectors.toList()));

            movie.setMovieGenres(movie.getMovieGenres().stream()
                    .map(genreService::findRecordByID)
                    .map(Genre::getGenreName)
                    .collect(Collectors.toList()));
        });

        model.addAttribute("movieList", movies.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movies.getTotalPages());

        List<Actor> actors = actorService.findAll();
        model.addAttribute("actors", actors);

        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        List<Language> languages = languageService.findAll();
        model.addAttribute("languages", languages);

        model.addAttribute("user", user);

        model.addAttribute("status", status);
        return new ModelAndView("panel/MovieList");
    }
}