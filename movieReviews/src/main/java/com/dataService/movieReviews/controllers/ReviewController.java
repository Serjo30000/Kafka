package com.dataService.movieReviews.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataService.movieReviews.models.reviews.MapperReview;
import com.dataService.movieReviews.models.reviews.ReviewDto;
import com.dataService.movieReviews.models.util.ReviewsAndDateDto;
import com.dataService.movieReviews.services.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MapperReview mapperReview;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll(){
        return ResponseEntity.ok(reviewService.getAll().stream()
            .map(mapperReview::map)
            .toList());
    }

    @GetMapping("/getAllReviewsByDate")
    public ResponseEntity<List<ReviewsAndDateDto>> getAllReviewsByDate() {
        return ResponseEntity.ok(reviewService.getAllReviewsByDate());
    }
}
