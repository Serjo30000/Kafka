package com.dataService.movieReviews.models.filmCritics;


import com.dataService.movieReviews.models.dtoReports.FIODto;
import com.dataService.movieReviews.models.reviews.Review;

import java.util.Set;
import java.time.LocalDate;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmCritic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String login;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false)),
            @AttributeOverride(name = "family", column = @Column(name = "family",  nullable = false)),
            @AttributeOverride(name = "patronymic", column = @Column(name ="patronymic"))
    })
    private FIODto fio;
    private LocalDate dateRegistration;
    @OneToMany(mappedBy = "filmCritic")
    private Set<Review> reviews;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((fio == null) ? 0 : fio.hashCode());
        result = prime * result + ((dateRegistration == null) ? 0 : dateRegistration.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FilmCritic other = (FilmCritic) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (fio == null) {
            if (other.fio != null)
                return false;
        } else if (!fio.equals(other.fio))
            return false;
        if (dateRegistration == null) {
            if (other.dateRegistration != null)
                return false;
        } else if (!dateRegistration.equals(other.dateRegistration))
            return false;
        return true;
    }
}
