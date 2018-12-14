package com.haulmont.testtask.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    List findAll(Class clazz);

    List findAllCountsForGenres();
}
