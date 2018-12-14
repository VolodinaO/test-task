package com.haulmont.testtask.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "genres")
public class Genre implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Long genreId;

        @Column(name = "genre_name", nullable = false)
        private String genreName;

    @OneToMany(mappedBy = "bookGenre", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<Book>();

    public Genre() {

    }

    public Genre(Long genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Long getId() {
        return genreId;
    }

    public void setId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return genreName;
    }

}
