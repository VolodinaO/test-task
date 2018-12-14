package com.haulmont.testtask.view.windows;

import com.haulmont.testtask.dao.GenreDAO;
import com.haulmont.testtask.dao.GenreDAOImpl;
import com.haulmont.testtask.tables.Genre;
import com.haulmont.testtask.view.NotificationUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public abstract class GenreWindow extends Window {

    private final FormLayout form = new FormLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final TextField genreNameField = new TextField("Название жанра");
    private final Button okButton = new Button("ОК", FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отмена", FontAwesome.CLOSE);
    private final GenreDAO dao = new GenreDAOImpl();
    private String genreName;
    private Genre genre;

    public GenreWindow() {
        super();
        formInit();
        setModal(true);
        setContent(form);
    }

    public Button getOkButton() {
        return okButton;
    }

    public FormLayout getForm() {
        return form;
    }

    public HorizontalLayout getButtonsLayout() {
        return buttonsLayout;
    }

    public TextField getGenreNameField() {
        return genreNameField;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public GenreDAO getDao() {
        return dao;
    }

    public String getGenreName() {
        return genreName;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
        genreNameField.setValue(genre.getGenreName());
    }

    private void formInit() {
        buttonsLayoutInit();
        form.addComponents(genreNameField, buttonsLayout);
        genreNameField.setRequired(true);
        form.setMargin(true);
        form.setSpacing(true);
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
        genreName = genreNameField.getValue().trim();
        if (dao.getByGenre(genreName) != null) {
            NotificationUtils
                    .showNotification("Жанр уже существует!");
            return false;
        }
        if (genre == null) {
            genre = new Genre();
        }
        genre.setGenreName(genreName);
        if (genreName.length() == 0) {
            NotificationUtils.showNotification("Введите название жанра!");
            return false;
        }
        return true;
    }
}

