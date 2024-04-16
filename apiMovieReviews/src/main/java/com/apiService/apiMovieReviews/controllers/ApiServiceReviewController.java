package com.apiService.apiMovieReviews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apiService.apiMovieReviews.dtos.MessageRes;
import com.apiService.apiMovieReviews.dtos.ReviewDto;
import com.apiService.apiMovieReviews.dtos.ReviewsAndDateDto;
import com.apiService.apiMovieReviews.services.KafkaMessageSender;
import com.apiService.apiMovieReviews.wrappers.RestTemplateClient;

import org.springframework.web.bind.annotation.CrossOrigin;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*",
    methods = {RequestMethod.GET,RequestMethod.POST})
public class ApiServiceReviewController {
    private final KafkaMessageSender kafkaMessageSender;
    private final RestTemplateClient restTemplateClient;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.review.topic}")
    private String topic;

    @PostMapping("/addReviewInMovie")
    public MessageRes addReview(@RequestBody ReviewDto dto){
        return kafkaMessageSender.send(topic,dto.getTitle(),dto);
    }

    @GetMapping
    public List<ReviewDto> getAll(){
        return restTemplateClient.requestLst(baseUrl+"/reviews", ReviewDto[].class);
    }

    @GetMapping("/getAllReviewsByDate")
    public List<ReviewsAndDateDto> getAllReviewsByDate() {
        var path = "/reviews/getAllReviewsByDate";
        return restTemplateClient.requestLst(baseUrl + path, ReviewsAndDateDto[].class);
    }
}
