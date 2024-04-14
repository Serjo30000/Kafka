package com.dataService.movieReviews.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataService.movieReviews.models.dtoReports.MovieAndEstimationDto;
import com.dataService.movieReviews.models.movies.MapperMovie;
import com.dataService.movieReviews.models.movies.MovieDto;
import com.dataService.movieReviews.services.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MapperMovie mapperMovie;

    @GetMapping
    public List<MovieDto> getAll(){
        return movieService.getAll().stream()
            .map(mapperMovie::map)
            .toList();
    }

    @GetMapping("/{title}")
    public MovieDto getByTitle(@PathVariable("title")String title){
        return mapperMovie.map(movieService.getByTitle(title));
    }

    @GetMapping("/getTop10MoviesAverageEstimation")
    public List<MovieAndEstimationDto> getTop10MoviesAverageEstimation() {
        return movieService.getTop10MoviesAverageEstimation();
    }
}
