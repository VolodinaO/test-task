package com.haulmont.testtask.view.tabs;


import com.haulmont.testtask.dao.GenreDAO;
import com.haulmont.testtask.dao.GenreDAOImpl;
import com.haulmont.testtask.tables.Genre;
import com.haulmont.testtask.view.NotificationUtils;
import com.haulmont.testtask.view.windows.AddGenreWindow;
import com.haulmont.testtask.view.windows.EditGenreWindow;
import com.haulmont.testtask.view.windows.GenreWindow;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;

public class GenresTab extends VerticalLayout implements ComponentContainer {
    private final Grid grid = new Grid();
    private final BeanItemContainer<Genre> genres = new BeanItemContainer<>(Genre.class);
    private final GenreDAO dao = new GenreDAOImpl();
    private final Button addButton = new Button("Добавить", FontAwesome.PLUS);
    private final Button editButton = new Button("Изменить", FontAwesome.EDIT);
    private final Button removeButton = new Button("Удалить", FontAwesome.REMOVE);
    private final Button showStatisticButton = new Button("Показать статистику", FontAwesome.BAR_CHART);
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    private final GenreWindow addGenreWindow = new AddGenreWindow();
    private final GenreWindow editGenreWindow = new EditGenreWindow();


    public GenresTab() {
        refreshTable();
        tableInit();
        setMargin(true);
        setSpacing(true);
        buttonLayoutInit();
        addComponents(grid, buttonLayout);
        addGenreWindow.addCloseListener(closeEvent -> refreshTable());
        editGenreWindow.addCloseListener(closeEvent -> refreshTable());
    }

    private void buttonLayoutInit() {
        buttonLayout.setSpacing(true);
        buttonLayout.setSizeUndefined();
        buttonLayout.addComponents(addButton, editButton, removeButton, showStatisticButton);
        addButton.addClickListener((Button.ClickEvent e) -> addGenre());
        editButton.addClickListener((Button.ClickEvent e) -> editGenre());
        removeButton.addClickListener((Button.ClickEvent e) -> removeGenre());
        showStatisticButton.addClickListener((Button.ClickEvent e) -> statisticForGenre());
    }

    private void addGenre() {
        UI.getCurrent().addWindow(addGenreWindow);
    }

    private void editGenre() {
        Genre genre = (Genre) grid.getSelectedRow();
        if (genre != null) {
            editGenreWindow.setGenre(genre);
            UI.getCurrent().addWindow(editGenreWindow);
        } else {
            genreNotSelectedWarning();
        }
    }

    private void statisticForGenre() {
        Genre genre = (Genre) grid.getSelectedRow();
        if (genre != null) {
            NotificationUtils.showNotificationStatistic("Количество книг жанра " + genre.getGenreName() +
                    " - " + String.valueOf(genre.getBooks().size()));
            refreshTable();
        } else {
            genreNotSelectedWarning();
        }
    }


    private void tableInit() {
        grid.setContainerDataSource(genres);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnOrder("genreName");
        grid.getColumn("genreName").setHeaderCaption("Название жанра");
        grid.getColumn("books").setHeaderCaption("Список книг");
        grid.removeColumn("id");
        grid.removeColumn("books");
        grid.setImmediate(true);
        grid.setHeight(100f, Sizeable.Unit.PERCENTAGE);
    }

    private void removeGenre() {
        final Genre genre = (Genre) grid.getSelectedRow();
        if (genre != null) {
            try {
                dao.delete(genre);
            } catch (ConstraintViolationException e) {
                NotificationUtils.showNotification("Нельзя удалить жанр, с которым существуют книги. " +
                        "Удалите книги с данным жанром и повторите попытку.");
            }  catch (PersistenceException e) {
                NotificationUtils.showNotification("Нельзя удалить жанр, с которым существуют книги. " +
                        "Удалите книги с данным жанром и повторите попытку.");
            } finally {
                refreshTable();
            }
        } else {
            genreNotSelectedWarning();
        }
    }

    private void genreNotSelectedWarning() {
        NotificationUtils.showNotification("Жанр не выбран!");
    }

    private void refreshTable() {
        genres.removeAllItems();
        genres.addAll(dao.findAll(Genre.class));
    }
}
