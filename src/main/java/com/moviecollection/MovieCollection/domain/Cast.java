package com.moviecollection.MovieCollection.domain;

import com.moviecollection.MovieCollection.entity.CastEntity;
import com.moviecollection.MovieCollection.entity.MovieEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Cast {
    private int castId;
    private String firstName;
    private String lastName;
//    private Movie movie;

    public static Cast fromEntity(CastEntity castEntity){
        return Cast.builder()
                .castId(castEntity.getId())
                .firstName(castEntity.getFirstName())
                .lastName(castEntity.getLastName())
//                .movie(Movie.fromEntity(castEntity.getMovieEntity()))
                .build();
    }
    public CastEntity toEntity (){
        return CastEntity.builder()
                .id(castId)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
    public static List<Cast> getMovieCastList(MovieEntity movieEntity){
        List<Cast> castList = new ArrayList<>();
        movieEntity.getCastList().forEach(castEntity -> {
            castList.add(Cast.fromEntity(castEntity));
        });
        return castList;
    }

}
