package com.moviecollection.MovieCollection.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "casts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CastEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "firstname" , length = 50)
    private String firstName;
    @Column(name = "lastname",length = 50)
    private String lastName;


    //I don't control casts like user,I just need name of casts
    @ManyToOne
    @JoinColumn(name = "movie_id",referencedColumnName = "id")
    private MovieEntity movieEntity;

}

