package com.apiService.apiMovieReviews.services;

import org.springframework.stereotype.Service;

import com.apiService.apiMovieReviews.dtos.FilmCriticDto;
import com.apiService.apiMovieReviews.dtos.MovieDto;
import com.apiService.apiMovieReviews.dtos.ReviewDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    public boolean checkReview(ReviewDto review, FilmCriticDto filmCritic, MovieDto movie){
        if (filmCritic == null || movie == null) {
            return false;
        }

        if (review.getEstimation() < 0 || review.getDate() == null || review.getComment().isEmpty() || review.getImdb().isEmpty() || review.getLogin().isEmpty()) {
            return false;
        }

        return true;
    }
}
