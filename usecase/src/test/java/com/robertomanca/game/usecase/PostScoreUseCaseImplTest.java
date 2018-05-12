package com.robertomanca.game.usecase;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.model.exception.SessionExpiredException;
import com.robertomanca.game.model.exception.SessionNotFoundException;
import com.robertomanca.game.repository.ScoreRepository;
import com.robertomanca.game.repository.SessionRepository;
import com.robertomanca.game.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class PostScoreUseCaseImplTest {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private ScoreRepository scoreRepository;
    private PostScoreUseCase postScoreUseCase;
    private static final Session SESSION;
    private static final User USER;
    private static final int USER_ID = 1234;
    private static final UUID UUID = java.util.UUID.randomUUID();

    static {
        USER = new User();
        USER.setUserId(USER_ID);
        USER.setEmail("email");
        USER.setName("mario");

        SESSION = new Session();
        SESSION.setUserId(USER_ID);
        SESSION.setKey(UUID);
    }

    @Before
    public void setUp() {

        userRepository = mock(UserRepository.class);
        sessionRepository = mock(SessionRepository.class);
        scoreRepository = mock(ScoreRepository.class);

        InjectorFactory.getInjectorConfigurator().configureSingleton(UserRepository.class, userRepository);
        InjectorFactory.getInjectorConfigurator().configureSingleton(SessionRepository.class, sessionRepository);
        InjectorFactory.getInjectorConfigurator().configureSingleton(ScoreRepository.class, scoreRepository);

        postScoreUseCase = new PostScoreUseCaseImpl();
    }

    @Test
    public void testPostScore() throws SessionExpiredException, SessionNotFoundException {

        when(userRepository.getUser(USER_ID)).thenReturn(USER);
        when(sessionRepository.getSession(UUID)).thenReturn(Optional.of(SESSION));

        postScoreUseCase.postScore(1, UUID, 500);
    }

    @Test(expected = SessionNotFoundException.class)
    public void testPostScoreSessionNotFound() throws SessionExpiredException, SessionNotFoundException {

        when(userRepository.getUser(USER_ID)).thenReturn(USER);
        when(sessionRepository.getSession(UUID)).thenReturn(Optional.empty());

        postScoreUseCase.postScore(1, UUID, 500);
    }

    @Test(expected = SessionExpiredException.class)
    public void testPostScoreSessionExpired() throws SessionExpiredException, SessionNotFoundException {

        when(userRepository.getUser(USER_ID)).thenReturn(USER);
        when(sessionRepository.getSession(UUID)).thenThrow(new SessionExpiredException());

        postScoreUseCase.postScore(1, UUID, 500);
    }
}
