package com.haulmont.testtask.view;

import com.haulmont.testtask.view.tabs.AuthorsTab;
import com.haulmont.testtask.view.tabs.BooksTab;
import com.haulmont.testtask.view.tabs.GenresTab;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private final TabSheet tabSheet = new TabSheet();
    private final AuthorsTab authorsTab = new AuthorsTab();
    private final GenresTab genresTab = new GenresTab();
    private final BooksTab booksTab = new BooksTab();

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        tabSheetInit();
        layout.addComponents(tabSheet);
        setContent(layout);
    }

    private void tabSheetInit() {
        tabSheet.setHeight(100f, Unit.PERCENTAGE);
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        tabSheet.addTab(authorsTab, "Авторы");
        tabSheet.addTab(genresTab, "Жанры");
        tabSheet.addTab(booksTab, "Книги");
    }
}