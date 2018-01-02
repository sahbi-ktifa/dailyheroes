package fr.efaya.game.dailyheroes.domain.pojo;

import fr.efaya.game.dailyheroes.controller.api.LootWebServiceController;

/**
 * @author Sahbi Ktifa
 * created on 02/01/2018
 */
public class LootedItem {
    private String itemId;
    private String itemName;
    private LootWebServiceController.ITEM_TYPE itemType;
    private boolean repeatable;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public LootWebServiceController.ITEM_TYPE getItemType() {
        return itemType;
    }

    public void setItemType(LootWebServiceController.ITEM_TYPE itemType) {
        this.itemType = itemType;
    }
}
