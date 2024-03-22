package com.dataService.movieReviews.exceptions;

public class FilmCriticNotFoundException extends RuntimeException{
    public FilmCriticNotFoundException(String e){
        super(e);
    }
}
