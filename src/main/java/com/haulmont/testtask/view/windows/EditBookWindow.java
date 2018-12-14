package com.haulmont.testtask.view.windows;

public class EditBookWindow extends BookWindow {

    public EditBookWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> updateBook());
    }

    private void updateBook() {
        if (super.isValidFieldData()) {
            this.getDao().update(getBook());
            this.close();
        }
    }
}
