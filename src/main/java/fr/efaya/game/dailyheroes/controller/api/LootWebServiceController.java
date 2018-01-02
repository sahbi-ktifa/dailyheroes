package fr.efaya.game.dailyheroes.controller.api;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Loot;
import fr.efaya.game.dailyheroes.domain.pojo.LootedItem;
import fr.efaya.game.dailyheroes.repository.ItemRepository;
import fr.efaya.game.dailyheroes.service.LootService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 24/12/2017
 */
@RestController
@RequestMapping("loot")
public class LootWebServiceController {

    public enum ITEM_TYPE {background, head, hair, gadget, face, clothes, other}

    private LootService lootService;
    private ItemRepository itemRepository;

    public LootWebServiceController(LootService lootService, ItemRepository itemRepository) {
        this.lootService = lootService;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ITEM_TYPE[] retrieveItemTypes() {
        return ITEM_TYPE.values();
    }

    @GetMapping("{username}")
    public List<LootedItem> retrieveLootsForUser(@PathVariable("username") String username) {
        List<Loot> loots = lootService.retrieveLoots(username);
        List<LootedItem> lootedItems = new ArrayList<>();
        for (Loot loot : loots) {
            LootedItem lootedItem = new LootedItem();
            lootedItem.setItemId(loot.getItemId());
            Item item = itemRepository.findOne(loot.getItemId());
            lootedItem.setItemName(item.getName());
            lootedItem.setRepeatable(item.isRepeatable());
            lootedItem.setItemType(item.getType());
            lootedItems.add(lootedItem);
        }
        return lootedItems;
    }
}
