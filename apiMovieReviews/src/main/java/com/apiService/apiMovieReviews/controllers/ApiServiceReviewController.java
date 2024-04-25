package com.apiService.apiMovieReviews.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apiService.apiMovieReviews.clientAPI.RestTemplateClient;
import com.apiService.apiMovieReviews.dtos.MessageRes;
import com.apiService.apiMovieReviews.dtos.ReviewDto;
import com.apiService.apiMovieReviews.dtos.ReviewsAndDateDto;
import com.apiService.apiMovieReviews.services.KafkaMessagePublisher;

import org.springframework.web.bind.annotation.CrossOrigin;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*",
    methods = {RequestMethod.GET,RequestMethod.POST})
public class ApiServiceReviewController {
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final RestTemplateClient restTemplateClient;
    
    @Value("${data-service.base-url}")
    private String baseUrl;

    @PostMapping("/addReviewInMovie")
    public ResponseEntity<MessageRes> addReview(@RequestBody ReviewDto dto){
        try {
            kafkaMessagePublisher.sendToReviewTopic(dto.getMovieUUID(), dto);
            return ResponseEntity.ok(new MessageRes("Accepted"));
        } 
        catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageRes("Internal server error"));
        }
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll(){
        return ResponseEntity.ok(restTemplateClient.requestLst(baseUrl+"/reviews", ReviewDto[].class));
    }

    @GetMapping("/getAllReviewsByDate")
    public ResponseEntity<List<ReviewsAndDateDto>> getAllReviewsByDate() {
        var path = "/reviews/getAllReviewsByDate";
        return ResponseEntity.ok(restTemplateClient.requestLst(baseUrl + path, ReviewsAndDateDto[].class));
    }
}
