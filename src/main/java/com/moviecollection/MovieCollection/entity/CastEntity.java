package com.moviecollection.MovieCollection.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

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


    //i dont control casts like user,i just need name of casts so i dont need many to many relationship
    @ManyToOne
    @JoinColumn(name = "movie_id",referencedColumnName = "id")
    private MovieEntity movieEntity;

}

