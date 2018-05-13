package com.robertomanca.game.web.score;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Level;
import com.robertomanca.game.model.Score;
import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.usecase.GetHighScoreUseCase;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Roberto Manca on 13-May-18.
 */
public class HighestScoreResourceImplTest {

    private static final String LEVEL = "1";
    private static final String LEVEL_NOT_VALID = "-12";
    private static final URI HIGH_SCORE_URI = URI.create("/" + LEVEL + "/highscorelist");
    private static final URI WRONG_URI = URI.create("/" + LEVEL_NOT_VALID + "/login");
    private GetHighScoreUseCase getHighScoreUseCase;
    private HighestScoreResource highestScoreResource;
    private HttpExchange httpExchange;
    private static final List<Score> SCORES;

    static {
        final ArrayList<Score> scores = new ArrayList<>();
        Score score = new Score();
        score.setScoreValue(1000);
        final Level level = new Level();
        level.setLevel(Integer.valueOf(LEVEL));
        score.setLevel(level);
        final User user = new User();
        user.setUserId(1234);
        score.setUser(user);
        SCORES = Collections.singletonList(score);
    }

    @Before
    public void setUp() {
        httpExchange = mock(HttpExchange.class);
        when(httpExchange.getResponseBody()).thenReturn(mock(OutputStream.class));

        getHighScoreUseCase = mock(GetHighScoreUseCase.class);

        InjectorFactory.getInjectorConfigurator().configureSingleton(GetHighScoreUseCase.class, getHighScoreUseCase);

        highestScoreResource = new HighestScoreResourceImpl();
    }

    @Test
    public void testGetHighestScoreWrongLevel() throws IOException {
        when(httpExchange.getRequestURI()).thenReturn(WRONG_URI);

        highestScoreResource.process(httpExchange);

        verify(getHighScoreUseCase, times(0)).getHighestScore(anyInt());
    }

    @Test
    public void testGetHighScoreEmpty() throws IOException {
        final Session session = new Session();
        session.setKey(UUID.randomUUID());
        when(httpExchange.getRequestURI()).thenReturn(HIGH_SCORE_URI);
        when(getHighScoreUseCase.getHighestScore(Integer.valueOf(LEVEL))).thenReturn(Collections.emptyList());

        highestScoreResource.process(httpExchange);

        verify(getHighScoreUseCase, times(1)).getHighestScore(Integer.valueOf(LEVEL));
    }

    @Test
    public void testGetHighScore() throws IOException {
        final Session session = new Session();
        session.setKey(UUID.randomUUID());
        when(httpExchange.getRequestURI()).thenReturn(HIGH_SCORE_URI);
        when(getHighScoreUseCase.getHighestScore(Integer.valueOf(LEVEL))).thenReturn(SCORES);

        highestScoreResource.process(httpExchange);

        verify(getHighScoreUseCase, times(1)).getHighestScore(Integer.valueOf(LEVEL));
    }
}
