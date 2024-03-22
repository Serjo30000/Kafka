package com.apiService.apiMovieReviews.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiService.apiMovieReviews.dtos.MovieAndEstimationDto;
import com.apiService.apiMovieReviews.dtos.MovieDto;
import com.apiService.apiMovieReviews.util.GetterRequest;
import com.apiService.apiMovieReviews.util.MessageRes;
import com.apiService.apiMovieReviews.util.Publisher;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class ApiServiceMovieController {
    private final Publisher publisher;
    private final GetterRequest getterRequest;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.movie.topic}")
    private String topic;

    @PostMapping("/addMovie")
    @ApiResponses({
        @ApiResponse(responseCode="201"),
        @ApiResponse(responseCode="500"),
        @ApiResponse(responseCode="422")
    })
    public ResponseEntity<MessageRes> addMovie(@RequestBody MovieDto dto){
        return publisher.send(topic,dto.getTitle() ,dto);       
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll(){
        return getterRequest.requestLst(baseUrl+"/movies", null, MovieDto.class);
    }

    @GetMapping("/{title}")
    @ApiResponses({
        @ApiResponse(responseCode="404",content = @Content(schema = @Schema())),
        @ApiResponse(responseCode="200")
    })
    public ResponseEntity<MovieDto> getByTitle(@PathVariable("title")String title){
        var path ="/movies/{title}";
        return getterRequest.request(baseUrl+path, 
            Map.of("title",
                        title), 
                MovieDto.class );
    }

    @GetMapping("/getTop10MoviesAverageEstimation")
    public ResponseEntity<List<MovieAndEstimationDto>> getTop10MoviesAverageEstimation() {

        var path = "/movies/getTop10MoviesAverageEstimation";
        return getterRequest.requestLst(baseUrl + path, null, MovieAndEstimationDto.class);
    }
}
