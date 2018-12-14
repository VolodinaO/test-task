package com.haulmont.testtask.view.tabs;

import com.haulmont.testtask.dao.AuthorDAO;
import com.haulmont.testtask.dao.AuthorDAOImpl;
import com.haulmont.testtask.tables.Author;
import com.haulmont.testtask.view.NotificationUtils;
import com.haulmont.testtask.view.windows.AddAuthorWindow;
import com.haulmont.testtask.view.windows.AuthorWindow;
import com.haulmont.testtask.view.windows.EditAuthorWindow;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;

public class AuthorsTab extends VerticalLayout implements ComponentContainer {
    private final Grid grid = new Grid();
    private final BeanItemContainer<Author> authors = new BeanItemContainer<>(Author.class);
    private final AuthorDAO dao = new AuthorDAOImpl();
    private final Button addButton = new Button("Добавить", FontAwesome.PLUS);
    private final Button editButton = new Button("Изменить", FontAwesome.EDIT);
    private final Button removeButton = new Button("Удалить", FontAwesome.REMOVE);
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    private final AuthorWindow addAuthorWindow = new AddAuthorWindow();
    private final AuthorWindow editAuthorWindow = new EditAuthorWindow();

    public AuthorsTab() {
        refreshTable();
        tableInit();
        setMargin(true);
        setSpacing(true);
        buttonLayoutInit();
        addComponents(grid, buttonLayout);
        addAuthorWindow.addCloseListener(closeEvent -> refreshTable());
        editAuthorWindow.addCloseListener(closeEvent -> refreshTable());
    }

    private void buttonLayoutInit() {
        buttonLayout.setSpacing(true);
        buttonLayout.setSizeUndefined();
        buttonLayout.addComponents(addButton, editButton, removeButton);
        addButton.addClickListener((Button.ClickEvent e) -> addAuthor());
        editButton.addClickListener((Button.ClickEvent e) -> editAuthor());
        removeButton.addClickListener((Button.ClickEvent e) -> removeAuthor());
    }

    private void addAuthor() {
        UI.getCurrent().addWindow(addAuthorWindow);
    }

    private void editAuthor() {
        Author author = (Author) grid.getSelectedRow();
        if (author != null) {
            editAuthorWindow.setAuthor(author);
            UI.getCurrent().addWindow(editAuthorWindow);
        } else {
            authorNotSelectedWarning();
        }
    }

    private void tableInit() {
        grid.setContainerDataSource(authors);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnOrder("secondName", "firstName", "patronymicName");
        grid.getColumn("secondName").setHeaderCaption("Фамилия");
        grid.getColumn("firstName").setHeaderCaption("Имя");
        grid.getColumn("patronymicName").setHeaderCaption("Отчество");
        grid.getColumn("books").setHeaderCaption("Список книг");
        grid.removeColumn("id");
        grid.removeColumn("books");
        grid.setImmediate(true);
        grid.setHeight(100f, Unit.PERCENTAGE);
    }

    private void removeAuthor() {
        final Author author = (Author) grid.getSelectedRow();
        if (author != null) {
            try {
                dao.delete(author);
            } catch (ConstraintViolationException e) {
                NotificationUtils.showNotification("Нельзя удалить автора, у которого существуют книги. " +
                        "Удалите его книги и повторите попытку.");
            }  catch (PersistenceException e) {
                NotificationUtils.showNotification("Нельзя удалить автора, у которого существуют книги. " +
                        "Удалите его книги и повторите попытку.");
            } finally {
                refreshTable();
            }
        } else {
            authorNotSelectedWarning();
        }
    }

    private void authorNotSelectedWarning() {
        NotificationUtils.showNotification("Автор не выбран!");
    }

    private void refreshTable() {
        authors.removeAllItems();
        authors.addAll(dao.findAll(Author.class));
    }
}
