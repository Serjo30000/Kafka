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

import com.apiService.apiMovieReviews.dtos.FIODto;
import com.apiService.apiMovieReviews.dtos.FilmCriticAndMoviesDto;
import com.apiService.apiMovieReviews.dtos.FilmCriticDto;
import com.apiService.apiMovieReviews.util.GetterRequest;
import com.apiService.apiMovieReviews.util.MessageRes;
import com.apiService.apiMovieReviews.util.Publisher;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/filmCritics")
@RequiredArgsConstructor
public class ApiServiceFilmCriticController {
    private final Publisher publisher;
    private final GetterRequest getterRequest;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.filmcritic.topic}")
    private String topic;

    @PostMapping("/addFilmCritic")
    @ApiResponses({
        @ApiResponse(responseCode="201"),
        @ApiResponse(responseCode="500"),
        @ApiResponse(responseCode="422")
    })
    public ResponseEntity<MessageRes> addFilmCritic(@RequestBody FilmCriticDto dto){
        
        return publisher.send(topic,dto.getLogin(),dto);
    }

    @GetMapping
    public ResponseEntity<List<FilmCriticDto>> getAll(){
        
        return getterRequest.requestLst(baseUrl+"/filmCritics", null, FilmCriticDto.class);

    }

    @GetMapping("/{login}")
    @ApiResponses({
        @ApiResponse(responseCode="404",content = @Content(schema = @Schema())),
        @ApiResponse(responseCode="200")
    })
    public ResponseEntity<FilmCriticDto> getById(@PathVariable("login")String login){
        var path ="/filmCritics/{login}";
        return getterRequest.request(baseUrl+path, 
            Map.of("login",login), 
                FilmCriticDto.class );
    }

    @GetMapping("/getFio")
    public ResponseEntity<List<FilmCriticDto>> getFilmCriticByFio(FIODto fDto){
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("/filmCritics/getFio?")
            .append("name=").append(fDto.getName())
            .append("&family=").append(fDto.getFamily())
            .append("&patronymic=").append(fDto.getPatronymic());       
        return getterRequest.requestLst(builder.toString(), null, FilmCriticDto.class);
    }

    @GetMapping("/getTop10MoviesCommentedFilmCritic")
    public ResponseEntity<List<FilmCriticAndMoviesDto>> getTop10MoviesCommentedFilmCritic() {
        var path = "/filmCritics/getTop10MoviesCommentedFilmCritic";
        return getterRequest.requestLst(baseUrl + path, null, FilmCriticAndMoviesDto.class);
    }
}
