package com.haulmont.testtask.dao;


import com.haulmont.testtask.tables.Author;

public interface AuthorDAO extends GenericDAO{
    Author getBySecondNameFirstNamePatronymicName(String secondName, String firstName, String patronymicName);
}
