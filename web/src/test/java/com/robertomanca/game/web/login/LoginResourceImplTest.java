package com.robertomanca.game.web.login;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Session;
import com.robertomanca.game.usecase.LoginUseCase;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Roberto Manca on 13-May-18.
 */
public class LoginResourceImplTest {

    private static final int USER_ID = 1;
    private static final URI LOGIN_URI = URI.create("/" + USER_ID + "/login");
    private static final URI WRONG_URI = URI.create("/-12/login");
    private LoginUseCase loginUseCase;
    private LoginResource loginResource;
    private HttpExchange httpExchange;

    @Before
    public void setUp() {
        httpExchange = mock(HttpExchange.class);
        when(httpExchange.getResponseBody()).thenReturn(mock(OutputStream.class));

        loginUseCase = mock(LoginUseCase.class);

        InjectorFactory.getInjectorConfigurator().configureSingleton(LoginUseCase.class, loginUseCase);

        loginResource = new LoginResourceImpl();
    }

    @Test
    public void testLoginWrongUserId() throws IOException {
        when(httpExchange.getRequestURI()).thenReturn(WRONG_URI);

        loginResource.process(httpExchange);

        verify(loginUseCase, times(0)).getSession(USER_ID);
    }

    @Test
    public void testLogin() throws IOException {
        final Session session = new Session();
        session.setKey(UUID.randomUUID());
        when(httpExchange.getRequestURI()).thenReturn(LOGIN_URI);
        when(loginUseCase.getSession(USER_ID)).thenReturn(session);

        loginResource.process(httpExchange);

        verify(loginUseCase, times(1)).getSession(USER_ID);
    }
}
