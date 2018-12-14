package com.haulmont.testtask.view.windows;


import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.tables.Author;
import com.haulmont.testtask.tables.Book;
import com.haulmont.testtask.tables.Genre;
import com.haulmont.testtask.view.NotificationUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class BookWindow extends Window {

    private final FormLayout form = new FormLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final Button okButton = new Button("OK", FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отмена", FontAwesome.CLOSE);
    private final BookDAO dao = new BookDAOImpl();
    private final TextField bookNameField = new TextField("Название");
    private final ComboBox authorCombo = new ComboBox("Автор");
    private final ComboBox genreCombo = new ComboBox("Жанр");
    private final ComboBox publisherCombo = new ComboBox("Издатель");
    private final TextField bookYearField = new TextField("Год");
    private final TextField bookCityField = new TextField("Город");

    private String bookName;
    private String bookPublisher;
    private int bookYear;
    private String bookCity;
    private Book book;
    private Genre genre;
    private Author author;
    private final AuthorDAO authorDAO = new AuthorDAOImpl();
    private final GenreDAO genreDAO = new GenreDAOImpl();

    private Calendar calendar = Calendar.getInstance();
    private int currentYear = calendar.get(Calendar.YEAR);

    public BookWindow() {
        super();
        formInit();
        setModal(true);
        setContent(form);
    }

    public FormLayout getForm() {
        return form;
    }

    public HorizontalLayout getButtonsLayout() {
        return buttonsLayout;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public BookDAO getDao() {
        return dao;
    }

    public TextField getBookNameField() {
        return bookNameField;
    }

    public ComboBox getAuthorCombo() {
        return authorCombo;
    }

    public ComboBox getGenreCombo() {
        return genreCombo;
    }

    public ComboBox getPublisherCombo() {
        return publisherCombo;
    }

//    public TextField getBookPublisherField() {
//        return bookPublisherField;
//    }

    public TextField getBookYearField() {
        return bookYearField;
    }

    public TextField getBookCityField() {
        return bookCityField;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public int getBookYear() {
        return bookYear;
    }

    public String getBookCity() {
        return bookCity;
    }

    public Author getAuthor() {
        return author;
    }

    public AuthorDAO getAuthorDAO() {
        return authorDAO;
    }

    public Genre getGenre() {
        return genre;
    }

    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    public Book getBook() {
        return book;
    }

    private void formInit() {
        buttonsLayoutInit();
        form.addComponents(bookNameField, authorCombo, genreCombo, publisherCombo, bookYearField, bookCityField, buttonsLayout);
        bookNameField.setRequired(true);
        authorCombo.setRequired(true);
        genreCombo.setRequired(true);
        publisherCombo.setRequired(true);
        bookYearField.setRequired(true);
        bookCityField.setRequired(true);
        form.setMargin(true);
        form.setSpacing(true);
        authorCombo.addFocusListener(focusEvent -> {
            List<Author> list = authorDAO.findAll(Author.class);
            authorCombo.removeAllItems();
            authorCombo.addItems(list);
        });
        genreCombo.addFocusListener(focusEvent -> {
            List<Genre> list = genreDAO.findAll(Genre.class);
            genreCombo.removeAllItems();
            genreCombo.addItems(list);
        });
        publisherCombo.addItems("Москва", "Питер", "O'Reilly");
    }

    private void buttonsLayoutInit() {
        buttonsLayout.addComponents(okButton, cancelButton);
        okButtonAddClickListener();
        cancelButton.addClickListener((Button.ClickEvent e) -> {
            this.close();
        });
        buttonsLayout.setSpacing(true);
    }

    protected abstract void okButtonAddClickListener();

    protected boolean isValidFieldData() {
        try {
            bookName = bookNameField.getValue().trim();
            author = (Author) authorCombo.getValue();
            genre = (Genre) genreCombo.getValue();
            bookPublisher = (String) publisherCombo.getValue();
            bookYear = Integer.parseInt(bookYearField.getValue().trim());
            bookCity = bookCityField.getValue().trim();

            List<TextField> fields = new ArrayList<>();
            fields.add(bookNameField);
            fields.add(bookYearField);
            fields.add(bookCityField);
            for (TextField field : fields) {
                if (field.getValue().length() == 0) {
                    NotificationUtils
                            .showNotification("Поле " + field.getCaption() + " не заполнено!"
                            );
                    return false;
                }
            }

            if (bookYear < 1000 || bookYear > currentYear){
                NotificationUtils
                        .showNotification("Год должен быть четырехзначным числом, не превышающим текущий год!"
                        );
                return false;
            }

            if (author == null) {
                NotificationUtils
                        .showNotification("Поле " + authorCombo.getCaption() + " не заполнено!"
                        );
                return false;
            }

            if (genre == null) {
                NotificationUtils
                        .showNotification("Поле " + genreCombo.getCaption() + " не заполнено!"
                        );
                return false;
            }

            if (bookPublisher == null) {
                NotificationUtils
                        .showNotification("Поле " + publisherCombo.getCaption() + " не заполнено!"
                        );
                return false;
            }

            if (book == null) {
                book = new Book();
            }
            book.setBookName(bookName);
            book.setAuthor(author);
            book.setBookGenre(genre);
            book.setBookPublisher(bookPublisher);
            book.setBookYear(bookYear);
            book.setBookCity(bookCity);
        }
        catch (NumberFormatException e) {
            NotificationUtils.showNotification("Введите год! Он должен быть целым положительным числом."
            );
            return false;
        }
        return true;
    }

    public void setBook(Book book) {
        this.book = book;
        populateFields();
    }

    private void populateFields() {
        bookNameField.setValue(book.getBookName());
        authorCombo.setValue(book.getAuthor());
        genreCombo.setValue(book.getGenre());
        publisherCombo.setValue(book.getBookPublisher());
        bookYearField.setValue(String.valueOf(book.getBookYear()));
        bookCityField.setValue(book.getBookCity());
    }
}
