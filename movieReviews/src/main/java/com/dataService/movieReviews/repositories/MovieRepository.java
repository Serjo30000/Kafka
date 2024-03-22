package com.dataService.movieReviews.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dataService.movieReviews.models.movies.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {
    Optional<Movie> findByTitle(String title);
    boolean existsByTitle(String title);
}
