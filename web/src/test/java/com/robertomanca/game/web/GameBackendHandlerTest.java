package com.robertomanca.game.web;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.web.login.LoginResource;
import com.robertomanca.game.web.score.HighestScoreResource;
import com.robertomanca.game.web.score.PostScoreResource;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Roberto Manca on 13-May-18.
 */
public class GameBackendHandlerTest {

    private static final URI LOGIN_URI = URI.create("/1/login");
    private static final URI POST_SCORE_URI = URI.create("/1/score?sessionkey=12345");
    private static final URI HIGH_SCORE_URI = URI.create("/1/highscorelist");
    private static final URI WRONG_URI = URI.create("fake/uri");
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private LoginResource loginResource;
    private PostScoreResource postScoreResource;
    private HighestScoreResource highestScoreResource;
    private GameBackendHandler gameBackendHandler;
    private HttpExchange httpExchange;

    @Before
    public void setUp() {
        httpExchange = mock(HttpExchange.class);
        when(httpExchange.getResponseBody()).thenReturn(mock(OutputStream.class));

        loginResource = mock(LoginResource.class);
        postScoreResource = mock(PostScoreResource.class);
        highestScoreResource = mock(HighestScoreResource.class);

        InjectorFactory.getInjectorConfigurator().configureSingleton(LoginResource.class, loginResource);
        InjectorFactory.getInjectorConfigurator().configureSingleton(PostScoreResource.class, postScoreResource);
        InjectorFactory.getInjectorConfigurator().configureSingleton(HighestScoreResource.class, highestScoreResource);

        gameBackendHandler = new GameBackendHandler();
    }

    @Test
    public void testGETResourceNotFoundWrongURI() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn(GET);
        when(httpExchange.getRequestURI()).thenReturn(WRONG_URI);

        gameBackendHandler.handle(httpExchange);

        verify(loginResource, times(0)).process(httpExchange);
        verify(postScoreResource, times(0)).process(httpExchange);
        verify(highestScoreResource, times(0)).process(httpExchange);
    }

    @Test
    public void testPOSTResourceNotFoundWrongURI() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn(POST);
        when(httpExchange.getRequestURI()).thenReturn(WRONG_URI);

        gameBackendHandler.handle(httpExchange);

        verify(loginResource, times(0)).process(httpExchange);
        verify(postScoreResource, times(0)).process(httpExchange);
        verify(highestScoreResource, times(0)).process(httpExchange);
    }

    @Test
    public void testResourceNotFoundWrongHTTPMethod() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn(DELETE);
        when(httpExchange.getRequestURI()).thenReturn(WRONG_URI);

        gameBackendHandler.handle(httpExchange);

        verify(loginResource, times(0)).process(httpExchange);
        verify(postScoreResource, times(0)).process(httpExchange);
        verify(highestScoreResource, times(0)).process(httpExchange);
    }

    @Test
    public void testLogin() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn(GET);
        when(httpExchange.getRequestURI()).thenReturn(LOGIN_URI);

        gameBackendHandler.handle(httpExchange);

        verify(loginResource, times(1)).process(httpExchange);
        verify(postScoreResource, times(0)).process(httpExchange);
        verify(highestScoreResource, times(0)).process(httpExchange);
    }

    @Test
    public void testPostScore() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn(POST);
        when(httpExchange.getRequestURI()).thenReturn(POST_SCORE_URI);

        gameBackendHandler.handle(httpExchange);

        verify(loginResource, times(0)).process(httpExchange);
        verify(postScoreResource, times(1)).process(httpExchange);
        verify(highestScoreResource, times(0)).process(httpExchange);
    }

    @Test
    public void testHighScore() throws IOException {
        when(httpExchange.getRequestMethod()).thenReturn(GET);
        when(httpExchange.getRequestURI()).thenReturn(HIGH_SCORE_URI);

        gameBackendHandler.handle(httpExchange);

        verify(loginResource, times(0)).process(httpExchange);
        verify(postScoreResource, times(0)).process(httpExchange);
        verify(highestScoreResource, times(1)).process(httpExchange);
    }
}
