package com.moviecollection.MovieCollection.entity;

import com.moviecollection.MovieCollection.enums.Gender;
import com.moviecollection.MovieCollection.security.ApplicationUserRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserEntity {

    @Id
    @SequenceGenerator(name = "seq_user_id",allocationSize = 1)
    @GeneratedValue(generator = "seq_user_id",strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "username",length = 50,unique = true,nullable = false)
    private String userName;
    @Column(name = "email",length = 50)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "joindate")
    private Date joinDate;

    @Column(name = "role") //ADMIN or USER
    private ApplicationUserRole role; //it is string (not ApplicationUserRole enum type) because i couldnt get the way of storing enum data as string.


    @ManyToMany
    @JoinTable(
            name = "users_created_movies",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id")
    )
    private List<MovieEntity> createdMovies;

}
