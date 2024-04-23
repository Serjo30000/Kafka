package com.dataService.movieReviews.models.filmCritics;

import com.dataService.movieReviews.models.dtoReports.FIODto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class FilmCriticDto {
    private String filmCriticUUID;
    private String login;
    private FIODto fio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRegistration;
}
