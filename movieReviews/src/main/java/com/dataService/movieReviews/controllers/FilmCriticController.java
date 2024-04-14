package com.dataService.movieReviews.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataService.movieReviews.models.dtoReports.FIODto;
import com.dataService.movieReviews.models.dtoReports.FilmCriticAndMoviesDto;
import com.dataService.movieReviews.models.filmCritics.FilmCriticDto;
import com.dataService.movieReviews.models.filmCritics.MapperFilmCritic;
import com.dataService.movieReviews.services.FilmCriticService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data/filmCritics")
@RequiredArgsConstructor
public class FilmCriticController {
    private final FilmCriticService filmCriticService;
    private final MapperFilmCritic mapperFilmCritic;

    @GetMapping
    public List<FilmCriticDto> getAll(){
        return filmCriticService.getAll().stream()
                .map(mapperFilmCritic::map)
                .toList();
    }

    @GetMapping("/{login}")
    public FilmCriticDto getByLogin(@PathVariable String login){
        return mapperFilmCritic.map(filmCriticService.getByLogin(login));
    }

    @GetMapping("/getFio")
    public List<FilmCriticDto> getByFio(FIODto fDto){
        return filmCriticService.getByFio(fDto).stream()
            .map(mapperFilmCritic::map)
            .toList();
    }

    @GetMapping("/getTop10MoviesCommentedFilmCritic")
    public List<FilmCriticAndMoviesDto> getTop10MoviesCommentedFilmCritic() {
        return filmCriticService.getTop10MoviesCommentedFilmCritic();
    }
}
