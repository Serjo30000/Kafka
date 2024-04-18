package com.apiService.apiMovieReviews.services;

import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.apiService.apiMovieReviews.dtos.FilmCriticDto;
import com.apiService.apiMovieReviews.dtos.MovieDto;
import com.apiService.apiMovieReviews.dtos.ReviewDto;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class KafkaMessagePublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka.filmcritic.topic}")
    private String filmCriticTopic;
    @Value("${kafka.movie.topic}")
    private String movieTopic;
    @Value("${kafka.review.topic}")
    private String reviewTopic;

    public void sendToFilmCriticTopic(String key, FilmCriticDto obj) throws InterruptedException, ExecutionException{
        kafkaTemplate.send(filmCriticTopic, key, obj).get();
    }

    public void sendToMovieTopic(String key, MovieDto obj) throws InterruptedException, ExecutionException {
        kafkaTemplate.send(movieTopic, key, obj).get();
    }

    public void sendToReviewTopic(String key, ReviewDto obj) throws InterruptedException, ExecutionException {
        kafkaTemplate.send(reviewTopic, key, obj).get();
    }
}
