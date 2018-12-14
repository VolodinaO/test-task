package com.haulmont.testtask.dao;


import com.haulmont.testtask.tables.Author;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class AuthorDAOImpl extends GenericDAOImpl implements AuthorDAO {

    @Override
    public Author getBySecondNameFirstNamePatronymicName(String secondName, String firstName, String patronymicName) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("from Author where secondName = :second_name and firstName = :first_name " +
                "and patronymicName = :patronymic_name");
        query.setString("second_name", secondName);
        query.setString("first_name", firstName);
        query.setString("patronymic_name", patronymicName);
        Author author = (Author) query.uniqueResult();
        session.getTransaction().commit();
        return author;
    }

}
