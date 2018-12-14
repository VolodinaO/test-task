package com.haulmont.testtask.view;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class NotificationUtils {

    public static Notification showNotification(String caption) {
        Notification notification = new Notification(caption, Notification.Type.WARNING_MESSAGE);
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
        return notification;
    }

    public static Notification showNotificationStatistic(String caption) {
        Notification notification = new Notification(caption, Notification.Type.HUMANIZED_MESSAGE);
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
        return notification;
    }
}
