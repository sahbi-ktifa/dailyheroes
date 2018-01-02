package fr.efaya.game.dailyheroes.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Document(collection = "Users")
public class User {
    @Id
    private String username;
    private String password;
    private Integer level = 1;
    private Integer currentExp = 0;
    private Date creationDate;
    private Map<String, String> avatar;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(Integer currentExp) {
        this.currentExp = currentExp;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Map<String, String> getAvatar() {
        return avatar;
    }

    public void setAvatar(Map<String, String> avatar) {
        this.avatar = avatar;
    }
}
