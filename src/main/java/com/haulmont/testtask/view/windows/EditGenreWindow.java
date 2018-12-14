package com.haulmont.testtask.view.windows;


public class EditGenreWindow extends GenreWindow{

    public EditGenreWindow() {
        super();
    }

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
