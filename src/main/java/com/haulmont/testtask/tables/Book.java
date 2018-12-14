package com.haulmont.testtask.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long bookId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_publisher", nullable = false)  //ввод только: Москва, Питер, O’Reilly
    private String bookPublisher;

    @Column(name = "book_year", nullable = false)
    private int bookYear;

    @Column(name = "book_city", nullable = false)
    private String bookCity;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author bookAuthor;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre bookGenre;

    public Book() {

    }

    public Book(String bookName, Author bookAuthor, Genre bookGenre,
                String bookPublisher, int bookYear, String bookCity) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.bookPublisher = bookPublisher;
        this.bookYear = bookYear;
        this.bookCity = bookCity;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Author getAuthor() {
        return bookAuthor;
    }

    public void setAuthor(Author bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Genre getGenre() {
        return bookGenre;
    }

    public void setBookGenre(Genre bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public String getBookCity() {
        return bookCity;
    }

    public void setBookCity(String bookCity) {
        this.bookCity = bookCity;
    }

    @Override
    public String toString() {
        return "tables.Book{" +
                "id=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor.getSecondName() + ' ' + bookAuthor.getFirstName() + ' ' + bookAuthor.getPatronymicName() + '\'' +
                ", bookGenre='" + bookGenre.getGenreName() + '\'' +
                ", bookPublisher=" + bookPublisher +
                ", bookYear=" + bookYear +
                ", bookCity=" + bookCity +
                '}';
    }

}
