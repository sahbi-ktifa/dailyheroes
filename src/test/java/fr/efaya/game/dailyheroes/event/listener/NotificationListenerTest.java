package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.Task;
import fr.efaya.game.dailyheroes.event.ValidatedTaskEvent;
import fr.efaya.game.dailyheroes.service.NotificationService;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * @author Sahbi Ktifa
 * created on 08/01/2018
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationListenerTest {
    @Mock private NotificationService notificationService;

    @InjectMocks private NotificationListener listener;

    @Test
    public void handleValidatedTaskEvent_WithNotification_ShouldNotifyNTimes() {
        Task task = new Task();
        task.setId("task1");
        when(notificationService.retrieveNotificationsUsingTask("task1")).thenReturn(Arrays.asList(new Notification(), new Notification()));
        listener.handleValidatedTaskEvent(new ValidatedTaskEvent(this, task, null));

        ArgumentCaptor<Notification> notification = ArgumentCaptor.forClass(Notification.class);
        verify(notificationService, times(2)).saveNotification(notification.capture());

        Assert.assertThat(notification.getAllValues().get(0).getRead(), IsEqual.equalTo(1));
        Assert.assertThat(notification.getAllValues().get(0).getValidated(), IsEqual.equalTo(true));
        Assert.assertThat(notification.getAllValues().get(1).getRead(), IsEqual.equalTo(1));
        Assert.assertThat(notification.getAllValues().get(1).getValidated(), IsEqual.equalTo(true));
    }
}