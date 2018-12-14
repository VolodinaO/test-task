package com.haulmont.testtask.view.windows;

public class EditAuthorWindow extends AuthorWindow {

    public EditAuthorWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> updateAuthor());
    }

    private void updateAuthor() {
        if (isValidFieldData()) {
            getDao().update(this.getAuthor());
            this.close();
        }
    }
}
