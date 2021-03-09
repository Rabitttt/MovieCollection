package com.moviecollection.MovieCollection.entity;

import com.moviecollection.MovieCollection.enums.Gender;
import com.moviecollection.MovieCollection.security.ApplicationUserRole;
import lombok.*;

import javax.persistence.*;
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
//    @Column(name = "joindate")
//    private Date joinDate;

    @Column(name = "role") //ADMIN or USER
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;

    @OneToMany(mappedBy = "creator")
    private  List<MovieEntity> createdMovies; //only creator can update movie

@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(
        name = "users_owned_movies",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id")
)
private List<MovieEntity> ownedMovies; //owned movies not self created but we want to show these movies in our list


}
