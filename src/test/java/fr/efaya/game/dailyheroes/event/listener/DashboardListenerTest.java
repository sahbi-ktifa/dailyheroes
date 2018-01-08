package fr.efaya.game.dailyheroes.event.listener;

import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.event.UserAddedInDashboard;
import fr.efaya.game.dailyheroes.service.NotificationService;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Sahbi Ktifa
 * created on 08/01/2018
 */
@RunWith(MockitoJUnitRunner.class)
public class DashboardListenerTest {

    @Mock private NotificationService notificationService;
    @InjectMocks private DashboardListener listener;

    @Test
    public void dashboardCreated_WhenEventIsReceived_ShouldNotifyUser() {
        User user = new User();
        user.setUsername("test");

        Dashboard dashboard = new Dashboard();
        dashboard.setName("dashboard name");
        dashboard.setId("1");
        UserAddedInDashboard event = new UserAddedInDashboard(this, null, user, dashboard);
        listener.dashboardCreated(event);

        ArgumentCaptor<Notification> notification = ArgumentCaptor.forClass(Notification.class);

        verify(notificationService, times(1)).saveNotification(notification.capture());

        Assert.assertThat(notification.getValue().getUsername(), IsEqual.equalTo("test"));
        Assert.assertThat(notification.getValue().getSuffix(), IsEqual.equalTo("dashboard name"));
        Assert.assertThat(notification.getValue().getExtra().get("dashboardId"), IsEqual.equalTo("1"));
    }
}