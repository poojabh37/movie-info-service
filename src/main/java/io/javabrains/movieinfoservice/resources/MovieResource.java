package io.javabrains.movieinfoservice.resources;

import io.javabrains.movieinfoservice.models.Movie;
import io.javabrains.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static io.javabrains.movieinfoservice.Constants.THE_MOVIE_DB_API_KEY_PARAMETER;
import static io.javabrains.movieinfoservice.Constants.THE_MOVIE_DB_SEARCH_MOVIE_URL;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${themoviedb.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable String movieId) {
        MovieSummary movieSummary = restTemplate.getForObject(buildSearchMovieURL(movieId), MovieSummary.class);
        if (movieSummary == null) return null;
        return new Movie(movieId, movieSummary.getOriginal_title(), movieSummary.getOverview());
    }

    private String buildSearchMovieURL(String movieId) {
        return THE_MOVIE_DB_SEARCH_MOVIE_URL + movieId + THE_MOVIE_DB_API_KEY_PARAMETER + apiKey;
    }

}
