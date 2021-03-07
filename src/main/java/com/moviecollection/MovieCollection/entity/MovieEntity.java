package com.moviecollection.MovieCollection.entity;

import com.moviecollection.MovieCollection.enums.MovieCategories;
import com.moviecollection.MovieCollection.enums.MovieLanguage;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@Builder
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
    @Enumerated(EnumType.STRING)
    private MovieCategories category;
    @Column(name = "language",length = 50)
    @Enumerated(EnumType.STRING)
    private MovieLanguage language;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private UserEntity owner;

    @OneToMany(mappedBy = "movieEntity")
    private List<CastEntity> castList;

}
