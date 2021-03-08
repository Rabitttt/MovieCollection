package com.moviecollection.MovieCollection.domain;


import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.enums.Gender;
import com.moviecollection.MovieCollection.security.ApplicationUserRole;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private int id;
    private String userName;
    private String email;
    private String password;
    private Gender gender;
    private ApplicationUserRole role;
    private List<Movie> movieList;
    private List<Movie> createdMovies;


    public static User fromEntity(UserEntity entity){
        return User.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .role(entity.getRole())
                .build();
    }
    public UserEntity toEntity()
    {
        return UserEntity.builder()
                .id(id)
                .userName(userName)
                .password(password)
                .email(email)
                .gender(gender)
                .role(role)
                .build();
    }

    public static List<Movie> userMoviesListFromEntity(UserEntity userEntity){
        List<Movie> movieList = new ArrayList<>();
        userEntity.getCreatedMovies().forEach(movie -> {
            movieList.add(Movie.fromEntity(movie));
        });
        return movieList;
    }
}
