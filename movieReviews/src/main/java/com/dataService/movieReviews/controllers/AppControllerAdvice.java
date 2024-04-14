package com.dataService.movieReviews.controllers;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dataService.movieReviews.exceptions.FilmCriticNotFoundException;
import com.dataService.movieReviews.exceptions.MovieNotFoundException;
import com.dataService.movieReviews.exceptions.dto.ResponseError;

@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseError handleException(MovieNotFoundException e){
        return new ResponseError(e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(FilmCriticNotFoundException.class)
    public ResponseError handleException(FilmCriticNotFoundException e) {
        return new ResponseError(e.getMessage(), LocalDateTime.now().toString());
    }
}
