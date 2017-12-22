package fr.efaya.game.todorpg.domain.pojo;

import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 20/12/2017
 */
public class Notification {
    private String id;
    private String message;

    public Notification(String message) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
