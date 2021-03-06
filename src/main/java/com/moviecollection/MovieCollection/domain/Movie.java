package com.moviecollection.MovieCollection.domain;

import com.moviecollection.MovieCollection.entity.MovieEntity;
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
    private String releaseDate;
    private MovieCategories category;
    private MovieLanguage language;
    private User owner;


    public static Movie fromEntity(MovieEntity movieEntity){
        return Movie.builder()
                .id(movieEntity.getId())
                .name(movieEntity.getName())
                .description(movieEntity.getDescription())
                .releaseDate(movieEntity.getReleaseDate().toString())
                .category(movieEntity.getCategory())
                .language(movieEntity.getLanguage())
                .build();
    }
    public MovieEntity toEntity()
    {
        return MovieEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(convertStringToTimestamp(releaseDate))
                .category(category)
                .language(language)
                .build();
    }
    public Timestamp convertStringToTimestamp(String strDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date = formatter.parse(strDate);
            return new Timestamp(date.getTime());

        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }
}
