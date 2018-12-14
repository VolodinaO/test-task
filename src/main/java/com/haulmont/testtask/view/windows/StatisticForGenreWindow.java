package com.haulmont.testtask.view.windows;


public class StatisticForGenreWindow extends GenreWindow {

    public StatisticForGenreWindow() { super(); }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> updateGenre());
    }

    private void updateGenre() {
        if (isValidFieldData()) {
            getDao().update(this.getGenre());
            this.close();
        }
    }
}
