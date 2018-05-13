package com.robertomanca.game.web.score;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.exception.SessionExpiredException;
import com.robertomanca.game.model.exception.SessionNotFoundException;
import com.robertomanca.game.usecase.PostScoreUseCase;
import com.robertomanca.game.web.util.HttpRequestBodyReader;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Roberto Manca on 13-May-18.
 */
public class PostScoreResourceImplTest {

    private static final int USER_ID = 1;
    private static final int LEVEL = 1;
    private static final int LEVEL_NOT_VALID = -12;
    private static final String VALID_SESSION_KEY = "81e1a3d3-fc7b-4ff2-8b42-8eb47e837922";
    private static final String WRONG_SESSION_KEY = "wrongkey";
    private static final URI POST_SCORE_URI = URI.create("/" + LEVEL + "/score?sessionkey=" + VALID_SESSION_KEY);
    private static final URI URI_WRONG_UUID = URI.create("/" + LEVEL + "/score?sessionkey=" + WRONG_SESSION_KEY);
    private static final URI URI_WRONG_LEVEL = URI.create("/" + LEVEL_NOT_VALID + "/score?sessionkey=" + VALID_SESSION_KEY);
    private static final String SCORE = "500";
    private PostScoreUseCase postScoreUseCase;
    private HttpRequestBodyReader httpRequestBodyReader;
    private PostScoreResource postScoreResource;
    private HttpExchange httpExchange;

    @Before
    public void setUp() {
        httpExchange = mock(HttpExchange.class);
        httpRequestBodyReader = mock(HttpRequestBodyReader.class);
        when(httpExchange.getResponseBody()).thenReturn(mock(OutputStream.class));

        postScoreUseCase = mock(PostScoreUseCase.class);

        InjectorFactory.getInjectorConfigurator().configureSingleton(PostScoreUseCase.class, postScoreUseCase);
        InjectorFactory.getInjectorConfigurator().configureSingleton(HttpRequestBodyReader.class, httpRequestBodyReader);

        postScoreResource = new PostScoreResourceImpl();
    }


    @Test
    public void testPostScore() throws IOException, SessionExpiredException, SessionNotFoundException {
        when(httpExchange.getRequestURI()).thenReturn(POST_SCORE_URI);
        final InputStream inputStream = mock(InputStream.class);
        when(httpExchange.getRequestBody()).thenReturn(inputStream);
        when(httpRequestBodyReader.read(any(InputStream.class))).thenReturn(SCORE);

        postScoreResource.process(httpExchange);

        verify(postScoreUseCase, times(1))
                .postScore(LEVEL, UUID.fromString(VALID_SESSION_KEY), Integer.valueOf(SCORE));
        verify(httpRequestBodyReader, times(1)).read(inputStream);
    }

    @Test
    public void testPostScoreWrongScore() throws IOException, SessionExpiredException, SessionNotFoundException {
        when(httpExchange.getRequestURI()).thenReturn(POST_SCORE_URI);
        final InputStream inputStream = mock(InputStream.class);
        when(httpExchange.getRequestBody()).thenReturn(inputStream);
        when(httpRequestBodyReader.read(any(InputStream.class))).thenReturn("mario");

        postScoreResource.process(httpExchange);

        verify(postScoreUseCase, times(0))
                .postScore(LEVEL, UUID.fromString(VALID_SESSION_KEY), Integer.valueOf(SCORE));
        verify(httpRequestBodyReader, times(1)).read(inputStream);
    }

    @Test
    public void testPostScoreWrongLevel() throws IOException, SessionExpiredException, SessionNotFoundException {
        when(httpExchange.getRequestURI()).thenReturn(URI_WRONG_LEVEL);
        final InputStream inputStream = mock(InputStream.class);
        when(httpExchange.getRequestBody()).thenReturn(inputStream);
        when(httpRequestBodyReader.read(any(InputStream.class))).thenReturn(SCORE);

        postScoreResource.process(httpExchange);

        verify(postScoreUseCase, times(0))
                .postScore(LEVEL, UUID.fromString(VALID_SESSION_KEY), Integer.valueOf(SCORE));
        verify(httpRequestBodyReader, times(1)).read(inputStream);
    }

    @Test
    public void testPostScoreWrongSessionKey() throws IOException, SessionExpiredException, SessionNotFoundException {
        when(httpExchange.getRequestURI()).thenReturn(URI_WRONG_UUID);
        final InputStream inputStream = mock(InputStream.class);
        when(httpExchange.getRequestBody()).thenReturn(inputStream);
        when(httpRequestBodyReader.read(any(InputStream.class))).thenReturn(SCORE);

        postScoreResource.process(httpExchange);

        verify(postScoreUseCase, times(0))
                .postScore(LEVEL, UUID.fromString(VALID_SESSION_KEY), Integer.valueOf(SCORE));
        verify(httpRequestBodyReader, times(1)).read(inputStream);
    }

    @Test
    public void testPostScoreSessionNotFound() throws IOException, SessionExpiredException, SessionNotFoundException {
        when(httpExchange.getRequestURI()).thenReturn(POST_SCORE_URI);
        final InputStream inputStream = mock(InputStream.class);
        when(httpExchange.getRequestBody()).thenReturn(inputStream);
        when(httpRequestBodyReader.read(any(InputStream.class))).thenReturn(SCORE);
        doThrow(new SessionNotFoundException()).when(postScoreUseCase).postScore(anyInt(), any(UUID.class), anyInt());

        postScoreResource.process(httpExchange);

        verify(postScoreUseCase, times(1))
                .postScore(LEVEL, UUID.fromString(VALID_SESSION_KEY), Integer.valueOf(SCORE));
        verify(httpRequestBodyReader, times(1)).read(inputStream);
    }

    @Test
    public void testPostScoreSessionExpired() throws IOException, SessionExpiredException, SessionNotFoundException {
        when(httpExchange.getRequestURI()).thenReturn(POST_SCORE_URI);
        final InputStream inputStream = mock(InputStream.class);
        when(httpExchange.getRequestBody()).thenReturn(inputStream);
        when(httpRequestBodyReader.read(any(InputStream.class))).thenReturn(SCORE);
        doThrow(new SessionExpiredException()).when(postScoreUseCase).postScore(anyInt(), any(UUID.class), anyInt());

        postScoreResource.process(httpExchange);

        verify(postScoreUseCase, times(1))
                .postScore(LEVEL, UUID.fromString(VALID_SESSION_KEY), Integer.valueOf(SCORE));
        verify(httpRequestBodyReader, times(1)).read(inputStream);
    }
}
