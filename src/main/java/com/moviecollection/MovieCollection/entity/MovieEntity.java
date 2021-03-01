package com.moviecollection.MovieCollection.entity;

import com.moviecollection.MovieCollection.enums.MovieCategories;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name" , length = 100)
    private String name;
    @Column(name = "description",length = 500)
    private String description;
    @Column(name = "releasedate")
    private Date releaseDate;
    @Column(name = "category")
    private MovieCategories category;
    @Column(name = "language",length = 50)
    private String language;

    @ManyToOne(fetch = FetchType.LAZY) //select when get method used
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
}
