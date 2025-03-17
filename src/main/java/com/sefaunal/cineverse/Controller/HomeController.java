package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Actor;
import com.sefaunal.cineverse.Model.Movie;
import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Service.ActorService;
import com.sefaunal.cineverse.Service.MovieService;
import com.sefaunal.cineverse.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

/**
 * @author github.com/sefaunal
 * @since 2025-01-11
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final MovieService movieService;
    private final ActorService actorService;

    private static final Integer REQUEST_SIZE = 10;

    @GetMapping("/")
    public RedirectView redirectToMovies() {
        return new RedirectView("/home/movies");
    }

    @GetMapping("/home")
    public RedirectView redirectToMoviesPage() {
        return new RedirectView("/home/movies");
    }

    @GetMapping("/home/movies")
    public ModelAndView moviesPage(Principal principal, Model model, @RequestParam(defaultValue = "1") int page) {
        if (principal == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findUserByUsername(principal.getName());
            model.addAttribute("user", user);
        }

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Movie> movies = movieService.findAllWithPageable(pageable);
        model.addAttribute("movies", movies.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movies.getTotalPages());

        return new ModelAndView("MoviesPage");
    }

    @GetMapping("/home/movies/search")
    public ModelAndView moviesPageWithSearch(Principal principal, Model model,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam String param) {
        if (principal == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findUserByUsername(principal.getName());
            model.addAttribute("user", user);
        }

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Movie> movies = movieService.searchMovies(param, pageable);
        model.addAttribute("movies", movies.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movies.getTotalPages());

        return new ModelAndView("MoviesPage");
    }

    @GetMapping("home/movies/details")
    public ModelAndView movieDetailsPage(Principal principal, Model model, @RequestParam String ID) {
        return new ModelAndView("MaintenancePage"); // TODO Implement Movie Details Page
    }

    @GetMapping("/home/movies/actor")
    public ModelAndView moviesPageListedByActors(Principal principal, Model model,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 @RequestParam("ID") String actorID) {
        if (principal == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findUserByUsername(principal.getName());
            model.addAttribute("user", user);
        }

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Movie> movies = movieService.findMoviesByActorID(actorID, pageable);
        model.addAttribute("movies", movies.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", movies.getTotalPages());

        return new ModelAndView("MoviesPage");
    }

    @GetMapping("/home/actors")
    public ModelAndView actorsPage(Principal principal, Model model, @RequestParam(defaultValue = "1") int page) {
        if (principal == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findUserByUsername(principal.getName());
            model.addAttribute("user", user);
        }

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Actor> actors = actorService.findAllWithPageable(pageable);
        model.addAttribute("actors", actors.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actors.getTotalPages());

        return new ModelAndView("ActorsPage");
    }

    @GetMapping("/home/actors/search")
    public ModelAndView actorsPageWithSearch(Principal principal, Model model,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam String param) {
        if (principal == null) {
            model.addAttribute("user", null);
        } else {
            User user = userService.findUserByUsername(principal.getName());
            model.addAttribute("user", user);
        }

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<Actor> actors = actorService.searchActors(param, pageable);
        model.addAttribute("actors", actors.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", actors.getTotalPages());

        return new ModelAndView("ActorsPage");
    }
}