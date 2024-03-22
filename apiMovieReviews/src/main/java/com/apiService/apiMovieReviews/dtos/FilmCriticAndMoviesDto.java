package com.apiService.apiMovieReviews.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class FilmCriticAndMoviesDto {
    private FilmCriticDto filmCritic;
    private List<MovieDto> movies;
    private long countMovies;
}
