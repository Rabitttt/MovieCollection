package com.moviecollection.MovieCollection.domain;

import com.moviecollection.MovieCollection.entity.MovieEntity;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.enums.MovieCategories;
import com.moviecollection.MovieCollection.enums.MovieLanguage;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private List<User> ownerList;
    private User creator;


    public static Movie fromEntity(MovieEntity movieEntity){
        return Movie.builder()
                .id(movieEntity.getId())
                .name(movieEntity.getName())
                .description(movieEntity.getDescription())
                .releaseDate(movieEntity.getReleaseDate())
                .category(movieEntity.getCategory())
                .language(movieEntity.getLanguage())
                .creator(User.fromEntity(movieEntity.getCreator()))
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
                .creator(creator.toEntity())
                .build();
    }

    public static List<User> movieOwnerListFromEntity(MovieEntity movieEntity){
        List<User> userList = new ArrayList<>();
        movieEntity.getOwnerList().forEach(userEntity -> {
            userList.add(User.fromEntity(userEntity));
        });
        return userList;
    }
}
