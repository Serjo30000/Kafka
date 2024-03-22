package com.dataService.movieReviews.services;

import com.dataService.movieReviews.exceptions.MovieNotFoundException;

import java.util.Comparator;
import org.springframework.stereotype.Service;
import com.dataService.movieReviews.models.movies.MapperMovie;
import com.dataService.movieReviews.models.movies.Movie;
import com.dataService.movieReviews.models.movies.MovieDto;
import com.dataService.movieReviews.models.reviews.MapperReview;
import com.dataService.movieReviews.models.reviews.Review;
import com.dataService.movieReviews.models.reviews.ReviewDto;
import com.dataService.movieReviews.models.util.MovieAndEstimationDto;
import com.dataService.movieReviews.repositories.MovieRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MapperMovie mapperMovie;
    private final MapperReview mapperReview;

    public List<Movie> getAll(){
        return movieRepository.findAll();
    }

    public Movie getByTitle(String title) {
        return movieRepository.findByTitle(title)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with title=" + title));
    }

    @Transactional
    public String saveMovie(Movie m) {
        movieRepository.save(m);
        return "Movie added";
    }

    public List<MovieAndEstimationDto> getTop10MoviesAverageEstimation(){
        return movieRepository.findAll().stream()
                .map(movie -> {
                    List<Review> reviews = movie.getReviews().stream().collect(Collectors.toList());
                    List<ReviewDto> reviewDtos = reviews.stream()
                            .map(mapperReview::map)
                            .toList();
                    double averageEstimation = reviews.stream()
                            .mapToInt(Review::getEstimation)
                            .average()
                            .orElse(0.0);
                    
                    MovieDto movieDto = mapperMovie.map(movie);
                    return MovieAndEstimationDto.builder()
                            .movieDto(movieDto)
                            .reviews(reviewDtos)
                            .averageEstimation(averageEstimation)
                            .build();
                })
                .sorted(Comparator.comparingDouble(MovieAndEstimationDto::getAverageEstimation).reversed())
                .limit(10)
                .toList();
    }
}
