package com.robertomanca.game.repository;

import com.robertomanca.game.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class UserRepositoryImplTest {

    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    public void testGetUserNotExisting() {
        final int userId = 1234;
        final User user = userRepository.getUser(userId);
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertNotNull(user.getEmail());
        assertFalse(user.getEmail().isEmpty());
        assertNotNull(user.getName());
        assertFalse(user.getName().isEmpty());
    }

    @Test
    public void testGetUserAlreadyExisting() {
        final int userId = 1234;
        final String name = "mario";
        final String email = "mario@gmail.com";

        final User user = userRepository.getUser(userId);
        user.setName(name);
        user.setEmail(email);

        final User retrieved = userRepository.getUser(userId);
        assertNotNull(retrieved);
        assertEquals(userId, retrieved.getUserId());
        assertNotNull(retrieved.getEmail());
        assertEquals(email, retrieved.getEmail());
        assertNotNull(retrieved.getName());
        assertEquals(name, retrieved.getName());
    }

    @Test
    public void testGetUserTwoConcurrent() throws InterruptedException, ExecutionException, TimeoutException {
        final int userId1 = 1234;
        final int userId2 = 9999;

        final ExecutorService executorService = Executors.newFixedThreadPool(2);


        final Future<User> userFuture1 = executorService.submit(() -> userRepository.getUser(userId1));
        final Future<User> userFuture2 = executorService.submit(() -> userRepository.getUser(userId2));

        final User user1 = userFuture1.get(5, TimeUnit.SECONDS);
        final User user2 = userFuture2.get(5, TimeUnit.SECONDS);

        assertNotNull(user1);
        assertNotNull(user2);
        assertEquals(userId1, user1.getUserId());
        assertNotNull(user1.getEmail());
        assertNotNull(user1.getName());

        assertEquals(userId2, user2.getUserId());
        assertNotNull(user2.getEmail());
        assertNotNull(user2.getName());
    }
}
