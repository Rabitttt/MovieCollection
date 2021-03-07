package com.moviecollection.MovieCollection.domain;

import com.moviecollection.MovieCollection.entity.MovieEntity;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.enums.MovieCategories;
import com.moviecollection.MovieCollection.enums.MovieLanguage;
import lombok.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Movie {
    private int id;
    private String name;
    private String description;
    private Date releaseDate;
    private MovieCategories category;
    private MovieLanguage language;
    private UserEntity owner;


    public static Movie fromEntity(MovieEntity movieEntity){
        return Movie.builder()
                .id(movieEntity.getId())
                .name(movieEntity.getName())
                .description(movieEntity.getDescription())
                .releaseDate(movieEntity.getReleaseDate())
                .category(movieEntity.getCategory())
                .language(movieEntity.getLanguage())
                .owner(movieEntity.getOwner())
                .build();
    }
    public MovieEntity toEntity()
    {
        return MovieEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .category(category)
                .language(language)
                .owner(owner)
                .build();
    }


}
