package com.robertomanca.game.usecase;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.repository.SessionRepository;
import com.robertomanca.game.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class LoginUseCaseImplTest {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private LoginUseCase loginUseCase;
    private static final Session SESSION = new Session();
    private static final User USER;
    private static final int USER_ID = 1234;

    static {
        USER = new User();
        USER.setUserId(USER_ID);
        USER.setEmail("email");
        USER.setName("mario");
    }

    @Before
    public void setUp() {

        userRepository = mock(UserRepository.class);
        sessionRepository = mock(SessionRepository.class);

        InjectorFactory.getInjectorConfigurator().configureSingleton(UserRepository.class, userRepository);
        InjectorFactory.getInjectorConfigurator().configureSingleton(SessionRepository.class, sessionRepository);

        loginUseCase = new LoginUseCaseImpl();
    }

    @Test
    public void testLogin() {

        when(userRepository.getUser(anyInt())).thenReturn(USER);
        when(sessionRepository.createSession(eq(USER), any(LocalDateTime.class))).thenReturn(SESSION);

        Session returned = loginUseCase.getSession(USER_ID);

        assertEquals(SESSION, returned);
    }
}
