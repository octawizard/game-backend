package com.robertomanca.game.repository;

import com.robertomanca.game.model.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public class UserRepositoryImpl implements UserRepository {

    private ConcurrentHashMap<Integer, User> users;

    public UserRepositoryImpl() {
        users = new ConcurrentHashMap<>();
    }

    @Override
    public User getUser(final int userId) {

        return Optional.ofNullable(users.get(userId))
                .orElse(generateAndStoreUser(userId));
    }

    private User generateAndStoreUser(final int userId) {
        final User generatedUser = generateUser(userId);
        users.put(userId, generatedUser);
        return generatedUser;
    }

    private User generateUser(final int userId) {

        final User user = new User();
        user.setUserId(userId);
        user.setName("mario");
        user.setEmail("mario@gmail.com");
        return user;
    }
}
