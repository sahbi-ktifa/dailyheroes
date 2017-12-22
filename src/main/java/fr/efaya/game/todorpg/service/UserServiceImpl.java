package fr.efaya.game.todorpg.service;

import fr.efaya.game.todorpg.domain.User;
import fr.efaya.game.todorpg.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User retrieveUser(String userId) {
        return repository.findOne(userId);
    }

    @Override
    public User retrieveUserUsingName(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        if (user.getCreationDate() == null) {
            user.setCreationDate(new Date());
        }
        return repository.save(user);
    }

    @Override
    public void consumeNotification(User user, String notificationId) {
        user.getNotifications().removeIf(n -> n.getId().equals(notificationId));
        repository.save(user);
    }
}
