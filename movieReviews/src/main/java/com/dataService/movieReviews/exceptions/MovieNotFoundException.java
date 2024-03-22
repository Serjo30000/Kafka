package com.dataService.movieReviews.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String e){
        super(e);
    }
}
