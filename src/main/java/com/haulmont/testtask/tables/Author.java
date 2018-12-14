package com.haulmont.testtask.tables;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "authors")
public class Author implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long authorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "patronymic_name", nullable = false)
    private String patronymicName;

    @OneToMany(mappedBy = "bookAuthor", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<Book>();

    public Author() {

    }

    public Author(Long authorId, String secondName, String firstName, String patronymicName) {
        this.authorId = authorId;
        this.secondName = secondName;
        this.firstName = firstName;
        this.patronymicName = patronymicName;
    }

    public Author(String secondName, String firstName, String patronymicName) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.patronymicName = patronymicName;
    }

    public Long getId() {
        return authorId;
    }

    public void setId(Long authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return secondName + " " + firstName + " " + patronymicName;
    }
}
