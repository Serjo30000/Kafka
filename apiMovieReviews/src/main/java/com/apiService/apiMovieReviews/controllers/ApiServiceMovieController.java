package com.apiService.apiMovieReviews.controllers;

import java.util.List;
import java.util.UUID;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.apiService.apiMovieReviews.clientAPI.RestTemplateClient;
import com.apiService.apiMovieReviews.dtos.MessageRes;
import com.apiService.apiMovieReviews.dtos.MovieAndEstimationDto;
import com.apiService.apiMovieReviews.dtos.MovieDto;
import com.apiService.apiMovieReviews.services.KafkaMessagePublisher;
import com.apiService.apiMovieReviews.services.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class ApiServiceMovieController {
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final RestTemplateClient restTemplateClient;
    private final MovieService movieService;
    
    @Value("${data-service.base-url}")
    private String baseUrl;

    @PostMapping("/addMovie")
    public ResponseEntity<MessageRes> addMovie(@RequestBody MovieDto dto){
        try {
            List<MovieDto> movies = restTemplateClient.requestLst(baseUrl+"/movies", MovieDto[].class);

            if (!movieService.checkMovie(dto, movies)){
                return ResponseEntity.ok(new MessageRes("Not accepted"));
            }

            UUID uuid = UUID.randomUUID();
            kafkaMessagePublisher.sendToMovieTopic(uuid.toString(), dto);
            
            return ResponseEntity.ok(new MessageRes("Accepted"));
        } 
        catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageRes("Internal server error"));
        }
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll(){
        return ResponseEntity.ok(restTemplateClient.requestLst(baseUrl+"/movies", MovieDto[].class));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/{imdb}")
    public ResponseEntity<MovieDto> getByImdb(@PathVariable("imdb")String imdb){
        var path ="/movies/{imdb}";
        return ResponseEntity.ok(restTemplateClient.request(baseUrl+path, MovieDto.class, imdb));
    }

    @GetMapping("/getTop10MoviesAverageEstimation")
    public ResponseEntity<List<MovieAndEstimationDto>> getTop10MoviesAverageEstimation() {

        var path = "/movies/getTop10MoviesAverageEstimation";
        return ResponseEntity.ok(restTemplateClient.requestLst(baseUrl + path, MovieAndEstimationDto[].class));
    }
}
