package fr.efaya.game.dailyheroes.domain.pojo;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Loot;

/**
 * @author Sahbi Ktifa
 * created on 04/01/2018
 */
public class LootedItemBuilder {
    private LootedItem lootedItem;

    private LootedItemBuilder() {
        this.lootedItem = new LootedItem();
    }

    public static LootedItemBuilder newInstance() {
        return new LootedItemBuilder();
    }

    public LootedItemBuilder withLoot(Loot loot) {
        this.lootedItem.setLootId(loot.getId());
        this.lootedItem.setRewardDate(loot.getRewardDate());
        this.lootedItem.setReceived(loot.getReceived());
        return this;
    }

    public LootedItemBuilder usingItem(Item item) {
        this.lootedItem.setItemId(item.getId());
        this.lootedItem.setItemName(item.getName());
        this.lootedItem.setRepeatable(item.isRepeatable());
        this.lootedItem.setItemType(item.getType());
        this.lootedItem.setSubType(item.getSubType());
        return this;
    }

    public LootedItem build() {
        return this.lootedItem;
    }
}
