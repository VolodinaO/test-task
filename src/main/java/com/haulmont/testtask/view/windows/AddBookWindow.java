package com.haulmont.testtask.view.windows;


import com.haulmont.testtask.tables.Book;

public class AddBookWindow extends BookWindow {

    public AddBookWindow() {
        super();
    }

    @Override
    protected void okButtonAddClickListener() {
        getOkButton().addClickListener(clickEvent -> addBook());
    }

    private void addBook() {
        if (super.isValidFieldData()) {
            Book book = new Book(getBookName(),getAuthor(), getGenre(), getBookPublisher(), getBookYear(), getBookCity());
            this.getDao().save(book);
            this.close();
        }
    }
}
