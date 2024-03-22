package com.dataService.movieReviews.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dataService.movieReviews.models.filmCritics.FilmCritic;
import com.dataService.movieReviews.models.util.FIODto;

@Repository
public interface FilmCriticRepository extends JpaRepository<FilmCritic, Integer>{
    List<FilmCritic> findByFio(FIODto fio);
    Optional<FilmCritic> findByLogin(String login);
    boolean existsByLogin(String login);
}
