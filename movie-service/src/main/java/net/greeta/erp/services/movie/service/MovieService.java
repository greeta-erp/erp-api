package net.greeta.erp.services.movie.service;

import net.greeta.erp.services.movie.model.Movie;

import java.util.List;

public interface MovieService {

    Movie validateAndGetMovie(String imdbId);

    List<Movie> getMovies();

    Movie saveMovie(Movie movie);

    void deleteMovie(Movie movie);
}