package fr.efaya.game.todorpg.service;

import fr.efaya.game.todorpg.domain.User;

/**
 * @author Sahbi Ktifa
 * created on 19/12/2017
 */
public interface UserService {
    User retrieveUser(String username);
    User saveUser(User user);
    void consumeNotification(User user, String notificationId);
}
