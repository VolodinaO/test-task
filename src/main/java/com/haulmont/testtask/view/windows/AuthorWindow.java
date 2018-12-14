package com.haulmont.testtask.view.windows;


import com.haulmont.testtask.dao.AuthorDAO;
import com.haulmont.testtask.dao.AuthorDAOImpl;
import com.haulmont.testtask.tables.Author;
import com.haulmont.testtask.view.NotificationUtils;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public abstract class AuthorWindow extends Window {

    private final FormLayout form = new FormLayout();
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final TextField secondNameField = new TextField("Фамилия");
    private final TextField firstNameField = new TextField("Имя");
    private final TextField patronymicNameField = new TextField("Отчество");
    private final Button okButton = new Button("OK", FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отмена", FontAwesome.CLOSE);
    private final AuthorDAO dao = new AuthorDAOImpl();
    private String secondName;
    private String firstName;
    private String patronymicName;
    private Author author;

    public AuthorWindow() {
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

    public TextField getSecondNameField() {
        return secondNameField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getPatronymicNameField() {
        return patronymicNameField;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public AuthorDAO getDao() {
        return dao;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
        secondNameField.setValue(author.getSecondName());
        firstNameField.setValue(author.getFirstName());
        patronymicNameField.setValue(author.getPatronymicName());
    }

    private void formInit() {
        buttonsLayoutInit();
        form.addComponents(secondNameField, firstNameField, patronymicNameField, buttonsLayout);
        secondNameField.setRequired(true);
        firstNameField.setRequired(true);
        patronymicNameField.setRequired(true);
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
            secondName = secondNameField.getValue().trim();
            firstName = firstNameField.getValue().trim();
            patronymicName = patronymicNameField.getValue().trim();
            if (dao.getBySecondNameFirstNamePatronymicName(secondName, firstName, patronymicName) != null) {
                NotificationUtils
                        .showNotification("Автор уже существует!");
                return false;
            }
            if (author == null) {
                author = new Author();
            }
            author.setSecondName(secondName);
            author.setFirstName(firstName);
            author.setPatronymicName(patronymicName);
            if (secondName.length() == 0) {
                NotificationUtils.showNotification("Введите фамилию автора!");
                return false;
            }
            if (firstName.length() == 0) {
                NotificationUtils.showNotification("Введите имя автора!");
                return false;
            }
            if (patronymicName.length() == 0) {
                NotificationUtils.showNotification("Введите отчество автора!");
                return false;
            }
        return true;
    }
}
