package com.dataService.movieReviews.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dataService.movieReviews.models.filmCritics.FilmCriticDto;
import com.dataService.movieReviews.models.filmCritics.MapperFilmCritic;
import com.dataService.movieReviews.models.movies.MapperMovie;
import com.dataService.movieReviews.models.movies.MovieDto;
import com.dataService.movieReviews.models.reviews.MapperReview;
import com.dataService.movieReviews.models.reviews.ReviewDto;
import com.dataService.movieReviews.services.FilmCriticService;
import com.dataService.movieReviews.services.MovieService;
import com.dataService.movieReviews.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopicListener {
    private final MovieService movieService;
    private final FilmCriticService filmCriticService;
    private final ReviewService reviewService;
    private final MapperMovie mapperMovie;
    private final MapperFilmCritic mapperFilmCritic;
    private final MapperReview mapperReview;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${kafka.movie.topic}",
        concurrency = "2", groupId = "${kafka.consumer.movie.id}")
    void consumeMovie(String dto) throws JsonMappingException{
        try {
            var d = mapper.readValue(dto,MovieDto.class);
            String res = movieService.saveMovie(mapperMovie.map(d));
            log.info(res);
        }catch(JsonProcessingException e){
            log.error("Mapping error:", e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.filmcritic.topic}",
        concurrency = "2", groupId = "${kafka.consumer.filmcritic.id}")
    void consumeFilmCritic(String dto) throws JsonMappingException{
        try {
            var d = mapper.readValue(dto,FilmCriticDto.class);
            String res = filmCriticService.saveFilmCritic(mapperFilmCritic.map(d));
            log.info(res);
        }catch(JsonProcessingException e){
            log.error("Mapping error:", e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.review.topic}",
        concurrency = "2", groupId = "${kafka.consumer.review.id}")
    void consumeReview(String dto) throws JsonMappingException{
        try {
            var d = mapper.readValue(dto,ReviewDto.class);
            String res = reviewService.saveReview(mapperReview.map(d));
            log.info(res);
        }catch(JsonProcessingException e){
            log.error("Mapping error:", e.getMessage());
        }
    }
}
