package com.dataService.movieReviews.models.filmCritics;

import org.springframework.stereotype.Component;

@Component
public class MapperFilmCritic {
    public FilmCritic map(FilmCriticDto dto) {
        return FilmCritic.builder()
                .login(dto.getLogin())
                .fio(dto.getFio())
                .dateRegistration(dto.getDateRegistration())
                .build();
    }

    public FilmCriticDto map(FilmCritic dto) {
        return FilmCriticDto.builder()
                .login(dto.getLogin())
                .fio(dto.getFio())
                .dateRegistration(dto.getDateRegistration())
                .build();
    }
}
