package com.dataService.movieReviews.models.movies;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class MovieDto {
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private String country;
    private String genre;
    private Integer rating;
    @JsonProperty("duration")
    private Integer duration;
}
