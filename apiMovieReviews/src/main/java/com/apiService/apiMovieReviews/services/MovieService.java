package com.apiService.apiMovieReviews.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apiService.apiMovieReviews.dtos.MovieDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
    public boolean checkMovie(MovieDto movie, List<MovieDto> movies){
        if (movie.getImdb().isEmpty() || movie.getTitle().isEmpty() || movie.getCountry().isEmpty()
                || movie.getRating() < 0 || movie.getGenre().isEmpty()
                || movie.getDuration() < 0 || movie.getCreateDate() == null) {
            return false;
        }

        for (MovieDto elemM : movies) {
            if (movie.getImdb().equals(elemM.getImdb())) {
                return false;
            }
        }

        return true;
    }
}
