package fr.efaya.game.dailyheroes.controller.api;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Loot;
import fr.efaya.game.dailyheroes.domain.pojo.LootedItem;
import fr.efaya.game.dailyheroes.domain.pojo.LootedItemBuilder;
import fr.efaya.game.dailyheroes.repository.ItemRepository;
import fr.efaya.game.dailyheroes.service.LootService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("items")
    public List<LootedItem> retrieveItems() {
        List<LootedItem> lootedItems = new ArrayList<>();
        Iterable<Item> items = itemRepository.findAll();
        if (items != null) {
            items.forEach(item -> {
                LootedItem lootedItem = LootedItemBuilder.newInstance()
                        .usingItem(item)
                        .build();
                lootedItems.add(lootedItem);
            });
        }
        return lootedItems;
    }

    @GetMapping("{username}/me")
    public List<LootedItem> retrieveLootsForUser(@PathVariable("username") String username) {
        List<Loot> loots = lootService.retrieveLoots(username);
        List<LootedItem> lootedItems = new ArrayList<>();
        for (Loot loot : loots) {
            Item item = itemRepository.findOne(loot.getItemId());
            LootedItem lootedItem = LootedItemBuilder.newInstance()
                    .withLoot(loot)
                    .usingItem(item)
                    .build();
            lootedItems.add(lootedItem);
        }
        return lootedItems;
    }

    @PostMapping("{lootId}")
    public void rewardReceived(@PathVariable("lootId") String lootId) {
        lootService.receiveLoot(lootId);
    }
}
