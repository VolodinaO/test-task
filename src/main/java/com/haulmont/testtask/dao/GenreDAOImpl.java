package com.haulmont.testtask.dao;

import com.haulmont.testtask.tables.Genre;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class GenreDAOImpl extends GenericDAOImpl implements GenreDAO {

    @Override
    public Genre getByGenre(String genreName) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from Genre where genreName = :genre_name");
        query.setString("genre_name", genreName);
        Genre genre = (Genre) query.uniqueResult();
        session.getTransaction().commit();
        return genre;
    }

}
