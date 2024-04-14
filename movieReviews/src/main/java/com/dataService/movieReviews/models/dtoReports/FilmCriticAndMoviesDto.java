package com.dataService.movieReviews.models.dtoReports;

import java.util.List;
import com.dataService.movieReviews.models.filmCritics.FilmCriticDto;
import com.dataService.movieReviews.models.movies.MovieDto;
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
