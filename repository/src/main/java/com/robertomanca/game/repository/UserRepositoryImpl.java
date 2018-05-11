package com.robertomanca.game.repository;

import com.robertomanca.game.model.User;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
        final User generatedUser = generateUser(userId);
        users.put(userId, generatedUser);
        return generatedUser;
    }

    private User generateUser(final int userId) {

        final User user = new User();
        user.setUserId(userId);
        user.setName(randomString());
        user.setEmail(randomString() + "@" + randomString());
        return user;
    }

    private String randomString() {
        byte[] array = new byte[8];
        RANDOM.nextBytes(array);
        return new String(array, Charset.defaultCharset());
    }
}
