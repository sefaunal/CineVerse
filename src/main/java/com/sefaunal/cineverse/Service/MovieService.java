package com.sefaunal.cineverse.Service;

import com.sefaunal.cineverse.Model.Movie;
import com.sefaunal.cineverse.Repository.MovieRepository;
import com.sefaunal.cineverse.Utils.CommonUtils;
import com.sefaunal.cineverse.Utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author github.com/sefaunal
 * @since 2025-02-22
 */

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public RedirectView saveMovie(Movie movie, MultipartFile movieImage) {
        if (movieRepository.findByMovieName(movie.getMovieName()).isPresent()) {
            return new RedirectView("/content/panel/movies/recordAlreadyExists");
        }

        String uniqueFileName = ImageUtils.generateUniqueFilename(
                movie.getMovieName(),
                Objects.requireNonNull(movieImage.getContentType())
        );
        String imageURI = ImageUtils.uploadImageToFirebase(movieImage, uniqueFileName);

        movie.setCreationDate(Instant.now());
        movie.setMovieImageURI(imageURI);

        movieRepository.save(movie);
        return new RedirectView("/content/panel/movies/additionSuccess");
    }

    public Page<Movie> findAllWithPageable(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Page<Movie> findMoviesByActorID(String ID, Pageable pageable) {
        return movieRepository.findAllByActorID(ID, pageable);
    }

    public Page<Movie> searchMovies(String searchParam, Pageable pageable) {
        List<Movie> potentialMatches = movieRepository.findAllByMovieName(searchParam);

        // Filter movies where searchParam is at least 40% of the movie name
        List<Movie> filteredMovies = potentialMatches.stream()
                .filter(movie -> {
                    String movieName = movie.getMovieName().toLowerCase();
                    int requiredLength = (int) Math.ceil(movieName.length() * 0.4); // 40% of movie name
                    return searchParam.length() >= requiredLength;
                })
                .collect(Collectors.toList());

        // Apply pagination manually
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredMovies.size());
        List<Movie> paginatedMovies = filteredMovies.subList(start, end);

        return new PageImpl<>(paginatedMovies, pageable, filteredMovies.size());
    }

    public Movie findRecordByID(String ID) {
        return movieRepository.findByID(ID).orElseThrow();
    }

    public RedirectView updateRecordByID(Movie movie, MultipartFile movieImage, String hashedPassword, String rawPassword) {
        if (!CommonUtils.checkPasswordsMatch(rawPassword, hashedPassword)) {
            return new RedirectView("/content/panel/movies/passwordsDoNotMatch");
        }

        if (!movieRepository.findByID(movie.getID()).orElseThrow().getMovieName().equals(movie.getMovieName())
                && movieRepository.findByMovieName(movie.getMovieName()).isPresent()) {
            return new RedirectView("/content/panel/movies/recordAlreadyExists");
        }

        if (movieImage != null) {
            String uniqueFileName = ImageUtils.generateUniqueFilename(movie.getMovieName(), Objects.requireNonNull(movieImage.getContentType()));
            String newImageURI = ImageUtils.uploadImageToFirebase(movieImage, uniqueFileName);
            movie.setMovieImageURI(newImageURI);
        }

        movieRepository.save(movie);
        return new RedirectView("/content/panel/movies/updateSuccess");
    }
}