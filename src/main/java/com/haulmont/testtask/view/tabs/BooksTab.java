package com.haulmont.testtask.view.tabs;

import com.haulmont.testtask.dao.BookDAO;
import com.haulmont.testtask.dao.BookDAOImpl;
import com.haulmont.testtask.tables.Book;
import com.haulmont.testtask.view.NotificationUtils;
import com.haulmont.testtask.view.windows.AddBookWindow;
import com.haulmont.testtask.view.windows.BookWindow;
import com.haulmont.testtask.view.windows.EditBookWindow;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public class BooksTab extends VerticalLayout implements ComponentContainer {

    private final Grid grid = new Grid();
    private final BeanItemContainer<Book> books = new BeanItemContainer<>(Book.class);
    private final BookDAO dao = new BookDAOImpl();
    private final Button addButton = new Button("Добавить", FontAwesome.PLUS);
    private final Button editButton = new Button("Изменить", FontAwesome.EDIT);
    private final Button removeButton = new Button("Удалить", FontAwesome.REMOVE);
    private final Button filterButton = new Button("Применить", FontAwesome.FILTER);
    private final HorizontalLayout filterPanel = new HorizontalLayout();
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    private final BookWindow addBookWindow = new AddBookWindow();
    private final BookWindow editBookWindow = new EditBookWindow();
    private final Label filterLabel = new Label("Фильтр");
    private final TextField filterPublisherField = new TextField("Издатель");
    private final TextField filterBookNameField = new TextField("Название");
    private TextField filterBookAuthorField = new TextField("Автор");

    public BooksTab() {
        refreshTable();
        tableInit();
        setMargin(true);
        setSpacing(true);
        buttonLayoutInit();
        filterPanelInit();
        addComponents(filterPanel, grid, buttonLayout);
        addBookWindow.addCloseListener(closeEvent -> refreshTable());
        editBookWindow.addCloseListener(closeEvent -> refreshTable());
    }

    private void buttonLayoutInit() {
        buttonLayout.setSpacing(true);
        buttonLayout.addComponents(addButton, editButton, removeButton);
        addButton.addClickListener((Button.ClickEvent e) -> addBook());
        editButton.addClickListener((Button.ClickEvent e) -> editBook());
        removeButton.addClickListener((Button.ClickEvent e) -> removeBook());
    }

    private void addBook() {
        UI.getCurrent().addWindow(addBookWindow);
    }

    private void editBook() {
        Book book = (Book) grid.getSelectedRow();
        if (book != null) {
            editBookWindow.setBook(book);
            UI.getCurrent().addWindow(editBookWindow);
        } else {
            bookNotSelectedWarning();
        }
    }

    private void removeBook() {
        Book book = (Book) grid.getSelectedRow();
        if (book != null) {
            dao.delete(book);
            refreshTable();
        } else {
            bookNotSelectedWarning();
        }
    }

    private void bookNotSelectedWarning() {
        NotificationUtils.showNotification("Книга не выбрана!");
    }

    private void refreshTable() {
        books.removeAllItems();
        books.addAll(dao.findAll(Book.class));
    }

    private void tableInit() {
        grid.setContainerDataSource(books);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnOrder("bookName", "author", "genre", "bookPublisher", "bookYear", "bookCity");
        grid.removeColumn("bookId");
        grid.getColumn("bookName").setHeaderCaption("Название");
        grid.getColumn("author").setHeaderCaption("Автор");
        grid.getColumn("genre").setHeaderCaption("Жанр");
        grid.getColumn("bookPublisher").setHeaderCaption("Издатель");
        grid.getColumn("bookYear").setHeaderCaption("Год");
        grid.getColumn("bookCity").setHeaderCaption("Город");
        grid.setImmediate(true);
    }

    private void addFilterToColumn(TextField textField, String columnId) {
     filterButton.addClickListener(new Button.ClickListener() {
            SimpleStringFilter filter = null;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Container.Filterable c = (Container.Filterable) grid.getContainerDataSource();

                if (filter != null)
                    c.removeContainerFilter(filter);

                filter =  new SimpleStringFilter(columnId, textField.getValue(),
                        true, false);
                c.addContainerFilter(filter);
            }
        });
        grid.cancelEditor();
    }

    private void filterBook() {
        if(filterBookNameField.isEmpty() || filterBookAuthorField.isEmpty()
                || filterPublisherField.isEmpty()){
            addFilterToColumn(filterBookNameField, "bookName");
            addFilterToColumn(filterBookAuthorField, "author");
            addFilterToColumn(filterPublisherField, "bookPublisher");
        } else {
            refreshTable();
        }
    }

    private void filterPanelInit() {
        filterPanel.setSpacing(true);
        filterPanel.addComponents(filterLabel, filterBookNameField, filterBookAuthorField, filterPublisherField, filterButton);
        filterPanel.setComponentAlignment(filterLabel, Alignment.MIDDLE_CENTER);
        filterPanel.setComponentAlignment(filterButton, Alignment.BOTTOM_RIGHT);
        filterButton.addClickListener((Button.ClickEvent e) -> filterBook());
    }
}
