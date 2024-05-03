package com.dataService.movieReviews.models.reviews;

import org.springframework.stereotype.Component;

import com.dataService.movieReviews.models.filmCritics.FilmCritic;
import com.dataService.movieReviews.models.movies.Movie;


@Component
public class MapperReview {
    public Review map(ReviewDto dto) {
        var review = new Review();
        review.setDate(dto.getDate());
        review.setEstimation(dto.getEstimation());
        review.setComment(dto.getComment());
        review.setMovie(Movie.builder()
                .imdb(dto.getImdb()).build());
        review.setFilmCritic(FilmCritic.builder()
                .login(dto.getLogin()).build());
        return review;
    }

    public ReviewDto map(Review dto) {
        return ReviewDto.builder()
                .imdb(dto.getMovie().getImdb())
                .login(dto.getFilmCritic().getLogin())
                .estimation(dto.getEstimation())
                .comment(dto.getComment())
                .date(dto.getDate())
                .build();
    }
}
