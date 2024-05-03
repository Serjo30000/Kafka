package com.apiService.apiMovieReviews.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class ReviewDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Integer estimation;
    private String comment;
    private String imdb;
    private String login;
}
