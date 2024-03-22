package com.apiService.apiMovieReviews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import com.apiService.apiMovieReviews.dtos.ReviewDto;
import com.apiService.apiMovieReviews.dtos.ReviewsAndDateDto;
import com.apiService.apiMovieReviews.util.GetterRequest;
import com.apiService.apiMovieReviews.util.MessageRes;
import com.apiService.apiMovieReviews.util.Publisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*",
    methods = {RequestMethod.GET,RequestMethod.POST})
public class ApiServiceReviewController {
    private final Publisher publisher;
    private final GetterRequest getterRequest;
    
    @Value("${data-service.base-url}")
    private String baseUrl;
    @Value("${kafka.review.topic}")
    private String topic;

    @PostMapping("/addReviewInMovie")
    @ApiResponses({
        @ApiResponse(responseCode="201"),
        @ApiResponse(responseCode="500"),
        @ApiResponse(responseCode="422")
    })
    public ResponseEntity<MessageRes> addReview(@RequestBody ReviewDto dto){
        return publisher.send(topic,dto.getTitle(),dto);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll(){
        return getterRequest.requestLst(baseUrl+"/reviews", null, ReviewDto.class);
    }

    @GetMapping("/getAllReviewsByDate")
    public ResponseEntity<List<ReviewsAndDateDto>> getAllReviewsByDate() {
        var path = "/reviews/getAllReviewsByDate";
        return getterRequest.requestLst(baseUrl + path, null, ReviewsAndDateDto.class);
    }
}
