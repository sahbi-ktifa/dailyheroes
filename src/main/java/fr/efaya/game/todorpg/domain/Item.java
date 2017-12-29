package fr.efaya.game.todorpg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
@Document(collection = "Items")
public class Item {
    
    @Id
    private String id;

    private String name;
    private Integer levelCap;
    private Integer rarity;
    private boolean repeatable;

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

    public Integer getLevelCap() {
        return levelCap;
    }

    public void setLevelCap(Integer levelCap) {
        this.levelCap = levelCap;
    }

    public Integer getRarity() {
        return rarity;
    }

    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }
}
