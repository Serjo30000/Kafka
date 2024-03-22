package com.dataService.movieReviews.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import java.util.List;
import com.dataService.movieReviews.models.reviews.MapperReview;
import com.dataService.movieReviews.models.reviews.Review;
import com.dataService.movieReviews.models.reviews.ReviewDto;
import com.dataService.movieReviews.models.util.ReviewsAndDateDto;
import com.dataService.movieReviews.repositories.FilmCriticRepository;
import com.dataService.movieReviews.repositories.MovieRepository;
import com.dataService.movieReviews.repositories.ReviewRepository;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final FilmCriticRepository filmCriticRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final MapperReview mapperReview;
    
    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    @Transactional
    public String saveReview(Review review){
        var fc = review.getFilmCritic();
        var m = review.getMovie();
        var fcFromDb = filmCriticRepository.findByLogin(fc.getLogin()).get();
        var mFromDb = movieRepository.findByTitle(m.getTitle()).get();
        review.setFilmCritic(fcFromDb);
        review.setMovie(mFromDb);
        var reviewsFc = Optional.ofNullable(fcFromDb.getReviews())
            .orElse(new HashSet<>());
        reviewsFc.add(review);
        fcFromDb.setReviews(reviewsFc);
        var reviewsM = Optional.ofNullable(mFromDb.getReviews())
            .orElse(new HashSet<>());
        reviewsM.add(review);
        mFromDb.setReviews(reviewsM);

        reviewRepository.save(review);
        return "Save review";
    }

    public List<ReviewsAndDateDto> getAllReviewsByDate() {
        List<Review> reviews = reviewRepository.findAll();
        Map<LocalDate, List<Review>> reviewsByDate = new HashMap<>();
        for (Review review : reviews) {
            LocalDate date = review.getDate();
            reviewsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(review);
        }
        return reviewsByDate.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<ReviewDto> reviewDtos = entry.getValue().stream()
                            .map(mapperReview::map)
                            .toList();
                    return ReviewsAndDateDto.builder()
                            .date(date)
                            .reviews(reviewDtos)
                            .build();
                })
                .toList();
    }
}
