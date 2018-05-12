package com.robertomanca.game.usecase;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Level;
import com.robertomanca.game.model.Score;
import com.robertomanca.game.model.User;
import com.robertomanca.game.repository.ScoreRepository;
import com.robertomanca.game.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class GetHighScoreUseCaseImplTest {

    private UserRepository userRepository;
    private ScoreRepository scoreRepository;
    private GetHighScoreUseCase getHighScoreUseCase;
    private static final List<Score> SCORES;
    private static final Level LEVEL;
    private static final User USER1;
    private static final User USER2;
    private static final int USER_ID_1 = 1234;
    private static final int USER_ID_2 = 9999;
    private static final int SCORE_1000 = 1000;
    private static final int SCORE_500 = 500;

    static {
        LEVEL = new Level();
        LEVEL.setLevel(1);

        USER1 = new User();
        USER1.setUserId(USER_ID_1);
        USER1.setEmail("emailMario");
        USER1.setName("mario");

        USER2 = new User();
        USER2.setUserId(USER_ID_2);
        USER2.setEmail("emailLuigi");
        USER2.setName("luigi");

        final Score score1 = new Score();
        final User user1 = new User();
        user1.setUserId(USER_ID_1);
        score1.setUser(user1);
        score1.setLevel(LEVEL);
        score1.setScoreValue(SCORE_500);

        final Score score2 = new Score();
        final User user2 = new User();
        user2.setUserId(USER_ID_2);
        score2.setUser(user2);
        score2.setLevel(LEVEL);
        score2.setScoreValue(SCORE_1000);

        SCORES = new ArrayList<>();
        SCORES.add(score2);
        SCORES.add(score1);
    }

    @Before
    public void setUp() {

        userRepository = mock(UserRepository.class);
        scoreRepository = mock(ScoreRepository.class);

        InjectorFactory.getInjectorConfigurator().configureSingleton(UserRepository.class, userRepository);
        InjectorFactory.getInjectorConfigurator().configureSingleton(ScoreRepository.class, scoreRepository);

        getHighScoreUseCase = new GetHighScoreUseCaseImpl();
    }

    @Test
    public void testGetHighestScore() {

        when(userRepository.getUser(USER_ID_1)).thenReturn(USER1);
        when(userRepository.getUser(USER_ID_2)).thenReturn(USER2);
        when(scoreRepository.getHighestScore(eq(LEVEL.getLevel()), anyInt())).thenReturn(SCORES);

        List<Score> scores = getHighScoreUseCase.getHighestScore(LEVEL.getLevel());
        assertEquals(SCORES.size(), scores.size());
        assertEquals(USER1.getName(), scores.get(1).getUser().getName());
        assertEquals(USER1.getEmail(), scores.get(1).getUser().getEmail());
        assertEquals(SCORE_500, scores.get(1).getScoreValue());
        assertEquals(USER2.getName(), scores.get(0).getUser().getName());
        assertEquals(USER2.getEmail(), scores.get(0).getUser().getEmail());
        assertEquals(SCORE_1000, scores.get(0).getScoreValue());
    }
}
