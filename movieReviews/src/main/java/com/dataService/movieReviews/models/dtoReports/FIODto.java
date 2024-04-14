package com.dataService.movieReviews.models.dtoReports;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FIODto {
    private String name;
    private String family;
    private String patronymic;
}
