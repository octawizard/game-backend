package com.robertomanca.game.repository;

import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.model.exception.SessionExpiredException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class SessionRepositoryImplTest {

    private SessionRepository sessionRepository;
    private static final User USER;
    private static final int USER_ID = 1234;

    static {
        USER = new User();
        USER.setUserId(USER_ID);
        USER.setName("mario");
        USER.setEmail("email");
    }

    @Before
    public void setUp() {
        sessionRepository = new SessionRepositoryImpl();
    }

    @Test
    public void testCreateSession() {
        final Session session = sessionRepository.createSession(USER, LocalDateTime.now());
        assertNotNull(session);
        assertNotNull(session.getKey());
        assertEquals(USER_ID, session.getUserId());
    }

    @Test
    public void testGetSession() throws SessionExpiredException {
        final Session session = sessionRepository.createSession(USER, LocalDateTime.now());
        assertNotNull(session);
        assertNotNull(session.getKey());

        final Optional<Session> sameSession = sessionRepository.getSession(session.getKey());
        assertTrue(sameSession.isPresent());
        assertEquals(session.getKey(), sameSession.get().getKey());
    }

    @Test
    public void testGetSessionMissing() throws SessionExpiredException {
        final Optional<Session> sameSession = sessionRepository.getSession(UUID.randomUUID());
        assertFalse(sameSession.isPresent());
    }

    @Test(expected = SessionExpiredException.class)
    public void testGetSessionExpired() throws SessionExpiredException {
        final Session session = sessionRepository.createSession(USER, LocalDateTime.now().minusHours(1));
        sessionRepository.getSession(session.getKey());
    }
}
