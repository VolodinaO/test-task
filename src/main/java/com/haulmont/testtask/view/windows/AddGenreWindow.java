package com.haulmont.testtask.view.windows;

import com.haulmont.testtask.tables.Genre;


public class AddGenreWindow extends GenreWindow {

    public AddGenreWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> addGenre());
    }

    private void addGenre() {
        if (super.isValidFieldData()) {
            Genre genre = new Genre(getGenreName());
            this.getDao().save(genre);
            this.close();
        }
    }

}
