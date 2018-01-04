package fr.efaya.game.dailyheroes.domain.builder;

import fr.efaya.game.dailyheroes.domain.Notification;

import java.util.Date;
import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 04/01/2018
 */
public class NotificationBuilder {
    private Notification notification;

    private NotificationBuilder() {
        this.notification = new Notification();
    }

    public static NotificationBuilder newInstance() {
        return new NotificationBuilder();
    }

    public NotificationBuilder withMessage(String message) {
        this.notification.setMessage(message);
        return this;
    }

    public NotificationBuilder forUser(String username) {
        this.notification.setUsername(username);
        return this;
    }

    public NotificationBuilder requireValidation(boolean requireValidation) {
        this.notification.setRequireValidation(requireValidation);
        return this;
    }

    public NotificationBuilder withTask(String taskId) {
        this.notification.setTaskId(taskId);
        return this;
    }

    public NotificationBuilder from(String from) {
        this.notification.setFrom(from);
        return this;
    }

    public NotificationBuilder withSuffix(String suffix) {
        this.notification.setSuffix(suffix);
        return this;
    }

    public NotificationBuilder addExtra(String key, String value) {
        this.notification.getExtra().put(key, value);
        return this;
    }

    public Notification build() {
        this.notification.setId(UUID.randomUUID().toString());
        this.notification.setCreationDate(new Date());
        return this.notification;
    }

}
