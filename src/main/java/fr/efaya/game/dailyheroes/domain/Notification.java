package fr.efaya.game.dailyheroes.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
@Document(collection = "Notifications")
public class Notification {
    @Id
    private String id;

    private Date creationDate;

    private String message;

    private String taskId;

    private String username;

    private int read;

    private Boolean requireValidation;

    private Boolean validated;

    public Notification() {
    }

    public Notification(String message, String username, String taskId) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.creationDate = new Date();
        this.username = username;
        this.taskId = taskId;
    }

    public Notification(String message, String username, String taskId, boolean requireValidation) {
        this.id = UUID.randomUUID().toString();
        this.creationDate = new Date();
        this.message = message;
        this.username = username;
        this.requireValidation = requireValidation;
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public Boolean getRequireValidation() {
        return requireValidation;
    }

    public void setRequireValidation(Boolean requireValidation) {
        this.requireValidation = requireValidation;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
}
