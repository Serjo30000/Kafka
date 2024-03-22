package com.dataService.movieReviews.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dataService.movieReviews.exceptions.FilmCriticNotFoundException;
import com.dataService.movieReviews.exceptions.MovieNotFoundException;
import com.dataService.movieReviews.exceptions.util.ResponseError;

@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ResponseError> handleException(MovieNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ResponseError(e.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(FilmCriticNotFoundException.class)
    public ResponseEntity<ResponseError> handleException(FilmCriticNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseError(e.getMessage(), LocalDateTime.now().toString()));
    }
}
