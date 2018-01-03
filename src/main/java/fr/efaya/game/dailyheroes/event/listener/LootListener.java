package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.event.CreatedUserEvent;
import fr.efaya.game.dailyheroes.event.LevelUpEvent;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.LootService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import fr.efaya.game.dailyheroes.service.UserService;
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
    private UserService userService;

    public LootListener(LootService lootService, NotificationService notificationService, UserService userService) {
        this.lootService = lootService;
        this.notificationService = notificationService;
        this.userService = userService;
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
        User user = userService.retrieveUser(event.getTask().getAssignedTo());
        if (user != null) {
            Item item = lootService.lootForTask(user, event.getTask());
            if (item != null) {
                notificationService.saveNotification(new Notification(String.format("Congratulations, you've been awarded with: '%s'.",
                        item.getName()), user.getUsername(), null));
            }
        }
    }

    @EventListener
    public void lootBasicItems(CreatedUserEvent event) {
        int count = lootService.lootBasicItems(event.getUser());
        if (count > 0) {
            notificationService.saveNotification(new Notification("New items have been unlocked, check your profile!", event.getUser().getUsername(), null));
        }
    }
}
