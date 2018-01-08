package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Item;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.event.LevelUpEvent;
import fr.efaya.game.dailyheroes.service.LootService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import fr.efaya.game.dailyheroes.service.UserService;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Sahbi Ktifa
 * created on 08/01/2018
 */
@RunWith(MockitoJUnitRunner.class)
public class LootListenerTest {
    @Mock private LootService lootService;
    @Mock private NotificationService notificationService;
    @Mock private UserService userService;

    @InjectMocks private LootListener listener;

    @Test
    public void handleLevelUpEvent_WithoutItem_ShouldNotNotify() {
        listener.handleLevelUpEvent(new LevelUpEvent(this, null, null));

        verify(lootService, times(1)).lootForLevel(any());
        verify(notificationService, times(0)).saveNotification(any());
    }

    @Test
    public void handleLevelUpEvent_WitItem_ShouldNotify() {
        Item item = new Item();
        item.setName("name1");
        when(lootService.lootForLevel(any())).thenReturn(item);

        Task task = new Task();
        task.setId("task1");
        User user = new User();
        user.setUsername("user1");
        ArgumentCaptor<Notification> notification = ArgumentCaptor.forClass(Notification.class);
        listener.handleLevelUpEvent(new LevelUpEvent(this, task, user));

        verify(lootService, times(1)).lootForLevel(any());

        verify(notificationService, times(1)).saveNotification(notification.capture());
        Assert.assertThat(notification.getValue().getUsername(), IsEqual.equalTo("user1"));
        Assert.assertThat(notification.getValue().getSuffix(), IsEqual.equalTo("name1"));
        Assert.assertThat(notification.getValue().getTaskId(), IsEqual.equalTo("task1"));
    }
}