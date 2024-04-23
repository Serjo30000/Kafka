package com.dataService.movieReviews.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dataService.movieReviews.models.dtoReports.FIODto;
import com.dataService.movieReviews.models.filmCritics.FilmCritic;

@Repository
public interface FilmCriticRepository extends JpaRepository<FilmCritic, Integer>{
    List<FilmCritic> findByFio(FIODto fio);
    Optional<FilmCritic> findByFilmCriticUUID(String filmCriticUUID);
    boolean existsByFilmCriticUUID(String filmCriticUUID);
}
