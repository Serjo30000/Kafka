package com.dataService.movieReviews.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dataService.movieReviews.models.reviews.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer>{
    
}
