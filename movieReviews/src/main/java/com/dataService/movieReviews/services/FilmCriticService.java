package com.dataService.movieReviews.services;


import java.util.Map;
import org.springframework.stereotype.Service;
import java.util.List;
import com.dataService.movieReviews.exceptions.FilmCriticNotFoundException;
import com.dataService.movieReviews.models.dtoReports.FIODto;
import com.dataService.movieReviews.models.dtoReports.FilmCriticAndMoviesDto;
import com.dataService.movieReviews.models.filmCritics.FilmCritic;
import com.dataService.movieReviews.models.filmCritics.MapperFilmCritic;
import com.dataService.movieReviews.models.movies.MapperMovie;
import com.dataService.movieReviews.models.movies.MovieDto;
import com.dataService.movieReviews.models.reviews.Review;
import com.dataService.movieReviews.repositories.FilmCriticRepository;
import com.dataService.movieReviews.repositories.ReviewRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class FilmCriticService {
    private final FilmCriticRepository filmCriticRepository;
    private final ReviewRepository reviewRepository;
    private final MapperMovie mapperMovie;
    private final MapperFilmCritic mapperFilmCritic;

    public List<FilmCritic> getAll(){
        return filmCriticRepository.findAll();
    }

    public List<FilmCritic> getByFio(FIODto fio){
        return filmCriticRepository.findByFio(fio);
    }

    public FilmCritic getByLogin(String login) {
        return filmCriticRepository.findByLogin(login)
                .orElseThrow(() -> new FilmCriticNotFoundException("FilmCritic not found with login=" + login));
    }

    @Transactional
    public String saveFilmCritic(FilmCritic fc) {
        if (fc.getFio().getName().isEmpty() || fc.getFio().getFamily().isEmpty()
                || fc.getFio().getPatronymic().isEmpty() 
                || fc.getLogin().isEmpty() || fc.getDateRegistration()==null){
            return "FilmCritic not added";
        }

        for (FilmCritic elemFc : filmCriticRepository.findAll()){
            if (fc.getLogin().equals(elemFc.getLogin())){
                return "FilmCritic not added";
            }
        }

        filmCriticRepository.save(fc);

        return "FilmCritic added";
    }

    public List<FilmCriticAndMoviesDto> getTop10MoviesCommentedFilmCritic(){
        List<Review> reviews = reviewRepository.findAll();
        Map<FilmCritic, Long> criticReviewCounts = new HashMap<>();
        for (Review review : reviews) {
            FilmCritic critic = review.getFilmCritic();
            criticReviewCounts.put(critic, criticReviewCounts.getOrDefault(critic, 0L) + 1);
        }
        List<FilmCriticAndMoviesDto> topCritics = new ArrayList<>();
        criticReviewCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEach(entry -> {
                    FilmCritic critic = entry.getKey();
                    Long reviewCount = entry.getValue();
                    List<MovieDto> movies = new ArrayList<>();
                    for (Review review : reviews) {
                        if (review.getFilmCritic().equals(critic)) {
                            movies.add(mapperMovie.map(review.getMovie()));
                        }
                    }
                    topCritics.add(FilmCriticAndMoviesDto.builder()
                            .filmCritic(mapperFilmCritic.map(critic))
                            .movies(movies)
                            .countMovies(reviewCount)
                            .build());
                });
        return topCritics;
    }
}
