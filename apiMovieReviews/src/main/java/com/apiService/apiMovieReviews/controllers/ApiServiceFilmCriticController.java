package com.apiService.apiMovieReviews.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiService.apiMovieReviews.clientAPI.RestTemplateClient;
import com.apiService.apiMovieReviews.dtos.FIODto;
import com.apiService.apiMovieReviews.dtos.FilmCriticAndMoviesDto;
import com.apiService.apiMovieReviews.dtos.FilmCriticDto;
import com.apiService.apiMovieReviews.dtos.MessageRes;
import com.apiService.apiMovieReviews.services.KafkaMessagePublisher;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/filmCritics")
@RequiredArgsConstructor
public class ApiServiceFilmCriticController {
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final RestTemplateClient restTemplateClient;
    
    @Value("${data-service.base-url}")
    private String baseUrl;

    @PostMapping("/addFilmCritic")
    public ResponseEntity<MessageRes> addFilmCritic(@RequestBody FilmCriticDto dto){
        try {
            kafkaMessagePublisher.sendToFilmCriticTopic(dto.getLogin(), dto);
            return ResponseEntity.ok(new MessageRes("Added data successfully"));
        } 
        catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageRes("Internal server error"));
        }
    }

    @GetMapping
    public ResponseEntity<List<FilmCriticDto>> getAll(){
        
        return ResponseEntity.ok(restTemplateClient.requestLst(baseUrl + "/filmCritics", FilmCriticDto[].class));

    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/{login}")
    public ResponseEntity<FilmCriticDto> getById(@PathVariable("login")String login){
        var path ="/filmCritics/{login}";
        return ResponseEntity.ok(restTemplateClient.request(baseUrl+path, FilmCriticDto.class, login));
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<FilmCriticDto>> getFilmCriticByFio(FIODto fDto){
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("/filmCritics/getFio?")
            .append("name=").append(fDto.getName())
            .append("&family=").append(fDto.getFamily())
            .append("&patronymic=").append(fDto.getPatronymic());       
        return ResponseEntity.ok(restTemplateClient.requestLst(builder.toString(), FilmCriticDto[].class));
    }

    @GetMapping("/getTop10MoviesCommentedFilmCritic")
    public ResponseEntity<List<FilmCriticAndMoviesDto>> getTop10MoviesCommentedFilmCritic() {
        var path = "/filmCritics/getTop10MoviesCommentedFilmCritic";
        return ResponseEntity.ok(restTemplateClient.requestLst(baseUrl + path, FilmCriticAndMoviesDto[].class));
    }
}
