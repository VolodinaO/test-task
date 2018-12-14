package com.haulmont.testtask.dao;


import com.haulmont.testtask.tables.Genre;

public interface GenreDAO extends GenericDAO{
    Genre getByGenre(String genreName);
}
