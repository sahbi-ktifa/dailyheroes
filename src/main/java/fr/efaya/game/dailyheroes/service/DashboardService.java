package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Dashboard;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface DashboardService {
    Dashboard saveDashboard(Dashboard dashboard);

    Dashboard retrieveDashboardForUser(String username);
}
