package com.dataService.movieReviews.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<FilmCriticDto>> getAll(){
        return ResponseEntity.ok(filmCriticService.getAll().stream()
                .map(mapperFilmCritic::map)
                .toList());
    }

    @GetMapping("/{filmCriticUUID}")
    public ResponseEntity<FilmCriticDto> getByFilmCriticUUID(@PathVariable String filmCriticUUID){
        return ResponseEntity.ok(mapperFilmCritic.map(filmCriticService.getByFilmCriticUUID(filmCriticUUID)));
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<FilmCriticDto>> getByFio(FIODto fDto){
        return ResponseEntity.ok(filmCriticService.getByFio(fDto).stream()
            .map(mapperFilmCritic::map)
            .toList());
    }

    @GetMapping("/getTop10MoviesCommentedFilmCritic")
    public ResponseEntity<List<FilmCriticAndMoviesDto>> getTop10MoviesCommentedFilmCritic() {
        return ResponseEntity.ok(filmCriticService.getTop10MoviesCommentedFilmCritic());
    }
}
