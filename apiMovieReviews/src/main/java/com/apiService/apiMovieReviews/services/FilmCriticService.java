package com.apiService.apiMovieReviews.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apiService.apiMovieReviews.dtos.FilmCriticDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmCriticService {
    public boolean checkFilmCritic(FilmCriticDto filmCritic, List<FilmCriticDto> filmCritics){
        if (filmCritic.getFio().getName().isEmpty() || filmCritic.getFio().getFamily().isEmpty()
                || filmCritic.getFio().getPatronymic().isEmpty()
                || filmCritic.getLogin().isEmpty() || filmCritic.getDateRegistration() == null) {
            return false;
        }

        for (FilmCriticDto elemFc : filmCritics) {
            if (filmCritic.getLogin().equals(elemFc.getLogin())) {
                return false;
            }
        }

        return true;
    }
}
