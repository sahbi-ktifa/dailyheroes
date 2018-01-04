package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Dashboard;

import java.util.List;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface DashboardService {
    Dashboard retrieveDashboard(String dashboardId);

    Dashboard saveDashboard(Dashboard dashboard);

    List<Dashboard> retrieveDashboardsForUser(String username);
}
