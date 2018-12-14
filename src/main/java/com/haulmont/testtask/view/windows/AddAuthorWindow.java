package com.haulmont.testtask.view.windows;

import com.haulmont.testtask.tables.Author;

public class AddAuthorWindow extends AuthorWindow {

    public AddAuthorWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> addAuthor());
    }

    private void addAuthor() {
        if (super.isValidFieldData()) {
            Author author = new Author(getSecondName(), getFirstName(), getPatronymicName());
            this.getDao().save(author);
            this.close();
        }
    }
}
