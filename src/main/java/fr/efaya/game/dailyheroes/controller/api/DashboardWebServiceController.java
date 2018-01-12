package fr.efaya.game.dailyheroes.controller.api;

import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.domain.Notification;
import fr.efaya.game.dailyheroes.domain.User;
import fr.efaya.game.dailyheroes.service.DashboardService;
import fr.efaya.game.dailyheroes.service.NotificationService;
import fr.efaya.game.dailyheroes.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 24/12/2017
 */
@RestController
@RequestMapping("dashboard")
public class DashboardWebServiceController {

    private DashboardService dashboardService;
    private UserService userService;
    private NotificationService notificationService;

    public DashboardWebServiceController(DashboardService dashboardService, UserService userService, NotificationService notificationService) {
        this.dashboardService = dashboardService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Dashboard> retrieveDashboardsForUser(Principal principal) {
        return dashboardService.retrieveDashboardsForUser(principal.getName());
    }

    @GetMapping("{dashboardId}")
    public Dashboard retrieveDashboardForUser(@PathVariable("dashboardId") String dashboardId, Principal principal) {
        Dashboard dashboard = dashboardService.retrieveDashboard(dashboardId);
        if (dashboard !=  null && dashboard.getUsers().contains(principal.getName())) {
            return dashboard;
        }
        return null;
    }

    @PostMapping("{dashboardId}")
    public void favoriteMe(@PathVariable("dashboardId") String dashboardId, Principal principal) {
        User user = userService.retrieveUser(principal.getName());
        if (!user.getFavoriteDashboard().equals(dashboardId)) {
            user.setFavoriteDashboard(dashboardId);
            userService.saveUser(user);
        }
    }

    @PostMapping("{notificationId}/{dashboardId}")
    public void joinAnotherDashboard(@PathVariable("notificationId") String notificationId, @PathVariable("dashboardId") String dashboardId, Principal principal) {
        Dashboard dashboard = dashboardService.retrieveDashboard(dashboardId);
        if (dashboard.getPendingUsers().contains(principal.getName())) {
            dashboard.getPendingUsers().remove(principal.getName());
            dashboard.getUsers().add(principal.getName());
            dashboardService.saveDashboard(dashboard);
            User user = userService.retrieveUser(principal.getName());
            if (user.getFavoriteDashboard() == null) {
                user.setFavoriteDashboard(dashboardService.retrieveDashboardsForUser(user.getUsername()).get(0).getId());
                userService.saveUser(user);
            }
            Notification notification = notificationService.retrieveNotification(notificationId);
            notification.setRead(1);
            notification.setValidated(true);
            notificationService.saveNotification(notification);
        }
    }
}
