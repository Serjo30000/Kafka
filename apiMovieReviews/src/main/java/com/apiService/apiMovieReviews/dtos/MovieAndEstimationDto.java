package com.apiService.apiMovieReviews.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MovieAndEstimationDto {
    MovieDto movieDto;
    List<ReviewDto> reviews;
    double averageEstimation;
}
