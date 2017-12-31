package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.event.LevelUpEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.LootService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Sahbi Ktifa
 * created on 29/12/2017
 */
@Component
public class LootListener {

    private LootService lootService;
    private NotificationService notificationService;

    public LootListener(LootService lootService, NotificationService notificationService) {
        this.lootService = lootService;
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleLevelUpEvent(LevelUpEvent event) {
        Item item = lootService.lootForLevel(event.getUser());
        if (item != null) {
            notificationService.saveNotification(new Notification(String.format("Congratulations, you've been awarded with: '%s'.",
                    item.getName()), event.getUser().getUsername(), event.getTask().getId()));
        }
    }

    @EventListener
    public void handleValidatedTaskEvent(ValidatedTaskEvent event) {
        Item item = lootService.lootForTask(event.getUser(), event.getTask());
        if (item != null) {
            notificationService.saveNotification(new Notification(String.format("Congratulations, you've been awarded with: '%s'.",
                    item.getName()), event.getUser().getUsername(), event.getTask().getId()));
        }
    }
}
