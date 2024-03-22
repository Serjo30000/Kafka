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
public class FilmCriticDto {
    private String login;
    private FIODto fio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRegistration;
}
