package com.dataService.movieReviews.models.dtoReports;


import java.time.LocalDate;
import com.dataService.movieReviews.models.reviews.ReviewDto;
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
