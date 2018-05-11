package com.robertomanca.game.repository;

import com.robertomanca.game.model.User;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static com.robertomanca.game.model.User.generateUser;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public class UserRepositoryImpl implements UserRepository {

    private static final Random RANDOM = new Random();
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
        final User generatedUser = generateUser(userId, RANDOM);
        users.put(userId, generatedUser);
        return generatedUser;
    }
}
