package fr.efaya.game.dailyheroes.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Document(collection = "Dashboards")
public class Dashboard {
    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    @NotNull
    @Min(2)
    private List<String> users = new ArrayList<>();

    private List<String> pendingUsers = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUsers() {
        return users;
    }

    public List<String> getPendingUsers() {
        return pendingUsers;
    }
}
