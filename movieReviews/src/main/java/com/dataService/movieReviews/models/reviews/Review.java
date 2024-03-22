package com.dataService.movieReviews.models.reviews;

import java.time.LocalDate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import com.dataService.movieReviews.models.filmCritics.FilmCritic;
import com.dataService.movieReviews.models.movies.Movie;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "idMovie",referencedColumnName = "id",nullable = false)
    private Movie movie;
    @ManyToOne
    @Cascade({ CascadeType.ALL })
    @JoinColumn(name = "idFilmCritic", referencedColumnName = "id", nullable = false)
    private FilmCritic filmCritic;
    private LocalDate date;
    private Integer estimation;
    private String comment;
}
