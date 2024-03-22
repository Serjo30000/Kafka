package com.apiService.apiMovieReviews.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsAndDateDto {
    List<ReviewDto> reviews;
    private LocalDate date;
}
