package com.dataService.movieReviews.models.util;

import java.util.List;

import com.dataService.movieReviews.models.movies.MovieDto;
import com.dataService.movieReviews.models.reviews.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MovieAndEstimationDto{
    MovieDto movieDto;
    List<ReviewDto> reviews;
    double averageEstimation;
}