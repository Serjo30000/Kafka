package com.apiService.apiMovieReviews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiService.apiMovieReviews.dtos.MessageRes;
import com.apiService.apiMovieReviews.dtos.MovieAndEstimationDto;
import com.apiService.apiMovieReviews.dtos.MovieDto;
import com.apiService.apiMovieReviews.services.KafkaMessageSender;
import com.apiService.apiMovieReviews.wrappers.RestTemplateClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class ApiServiceMovieController {
    private final KafkaMessageSender kafkaMessageSender;
    private final RestTemplateClient restTemplateClient;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.movie.topic}")
    private String topic;

    @PostMapping("/addMovie")
    public MessageRes addMovie(@RequestBody MovieDto dto){
        return kafkaMessageSender.send(topic,dto.getTitle() ,dto);       
    }

    @GetMapping
    public List<MovieDto> getAll(){
        return restTemplateClient.requestLst(baseUrl+"/movies", MovieDto[].class);
    }

    @GetMapping("/{title}")
    public MovieDto getByTitle(@PathVariable("title")String title){
        var path ="/movies/{title}";
        return restTemplateClient.request(baseUrl+path, MovieDto.class, title);
    }

    @GetMapping("/getTop10MoviesAverageEstimation")
    public List<MovieAndEstimationDto> getTop10MoviesAverageEstimation() {

        var path = "/movies/getTop10MoviesAverageEstimation";
        return restTemplateClient.requestLst(baseUrl + path, MovieAndEstimationDto[].class);
    }
}
