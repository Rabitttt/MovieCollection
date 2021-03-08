package com.moviecollection.MovieCollection.entity;

import com.moviecollection.MovieCollection.enums.MovieCategories;
import com.moviecollection.MovieCollection.enums.MovieLanguage;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "creator_id",referencedColumnName = "id")
    private UserEntity creator;


    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "ownedMovies")
    private List<UserEntity> ownerList;

    @OneToMany(mappedBy = "movieEntity")
    private List<CastEntity> castList;

}
