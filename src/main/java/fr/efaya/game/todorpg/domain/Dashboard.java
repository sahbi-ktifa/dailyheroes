package fr.efaya.game.todorpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Document(collection = "Dashboards")
public class Dashboard {
    @Id
    private String id;

    @NotNull
    @Min(2)
    private List<String> users = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
