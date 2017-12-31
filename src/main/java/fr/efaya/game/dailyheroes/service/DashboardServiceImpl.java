package fr.efaya.game.dailyheroes.service;

import fr.efaya.game.dailyheroes.domain.Dashboard;
import fr.efaya.game.dailyheroes.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Service
public class DashboardServiceImpl implements DashboardService {
    private DashboardRepository repository;

    public DashboardServiceImpl(DashboardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Dashboard saveDashboard(Dashboard dashboard) {
        if (dashboard.getId() == null) {
            dashboard.setId(UUID.randomUUID().toString());
        }
        return repository.save(dashboard);
    }

    @Override
    public Dashboard retrieveDashboardForUser(String username) {
        return repository.findByUsersContains(username);
    }
}
