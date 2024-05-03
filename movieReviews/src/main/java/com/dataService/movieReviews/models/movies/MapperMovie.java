package com.dataService.movieReviews.models.movies;

import org.springframework.stereotype.Component;

@Component
public class MapperMovie {
    public Movie map(MovieDto dto) {
        return Movie.builder()
                .imdb(dto.getImdb())
                .title(dto.getTitle())
                .createDate(dto.getCreateDate())
                .country(dto.getCountry())
                .genre(dto.getGenre())
                .rating(dto.getRating())
                .duration(dto.getDuration())
                .build();
    }

    public MovieDto map(Movie dto) {
        return MovieDto.builder()
                .imdb(dto.getImdb())
                .title(dto.getTitle())
                .createDate(dto.getCreateDate())
                .country(dto.getCountry())
                .genre(dto.getGenre())
                .rating(dto.getRating())
                .duration(dto.getDuration())
                .build();
    }
}
