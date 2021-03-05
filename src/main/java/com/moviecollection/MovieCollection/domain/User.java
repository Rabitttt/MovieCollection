package com.moviecollection.MovieCollection.domain;


import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.enums.Gender;
import com.moviecollection.MovieCollection.security.ApplicationUserRole;
import lombok.*;

import javax.persistence.Enumerated;
import java.util.Date;

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
    private Date joinDate;
    private ApplicationUserRole role;

    //filmler gelecek

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

}
