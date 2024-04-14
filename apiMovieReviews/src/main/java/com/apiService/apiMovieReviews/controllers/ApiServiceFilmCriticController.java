package com.apiService.apiMovieReviews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiService.apiMovieReviews.dtos.FIODto;
import com.apiService.apiMovieReviews.dtos.FilmCriticAndMoviesDto;
import com.apiService.apiMovieReviews.dtos.FilmCriticDto;
import com.apiService.apiMovieReviews.dtos.MessageRes;
import com.apiService.apiMovieReviews.services.KafkaMessageSender;
import com.apiService.apiMovieReviews.wrappers.RestTemplateClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/filmCritics")
@RequiredArgsConstructor
public class ApiServiceFilmCriticController {
    private final KafkaMessageSender publisher;
    private final RestTemplateClient getterRequest;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.filmcritic.topic}")
    private String topic;

    @PostMapping("/addFilmCritic")
    public MessageRes addFilmCritic(@RequestBody FilmCriticDto dto){
        
        return publisher.send(topic,dto.getLogin(),dto);
    }

    @GetMapping
    public List<FilmCriticDto> getAll(){
        
        return getterRequest.requestLst(baseUrl+"/filmCritics", FilmCriticDto[].class);

    }

    @GetMapping("/{login}")
    public FilmCriticDto getById(@PathVariable("login")String login){
        var path ="/filmCritics/{login}";
        return getterRequest.request(baseUrl+path, FilmCriticDto.class, login);
    }

    @GetMapping("/getFio")
    public List<FilmCriticDto> getFilmCriticByFio(FIODto fDto){
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("/filmCritics/getFio?")
            .append("name=").append(fDto.getName())
            .append("&family=").append(fDto.getFamily())
            .append("&patronymic=").append(fDto.getPatronymic());       
        return getterRequest.requestLst(builder.toString(), FilmCriticDto[].class);
    }

    @GetMapping("/getTop10MoviesCommentedFilmCritic")
    public List<FilmCriticAndMoviesDto> getTop10MoviesCommentedFilmCritic() {
        var path = "/filmCritics/getTop10MoviesCommentedFilmCritic";
        return getterRequest.requestLst(baseUrl + path, FilmCriticAndMoviesDto[].class);
    }
}
