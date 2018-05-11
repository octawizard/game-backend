package com.robertomanca.game.repository;

import com.robertomanca.game.model.Score;
import com.robertomanca.game.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class ScoreRepositoryImplTest {

    private ScoreRepository scoreRepository;

    @Before
    public void setUp() {
        scoreRepository = new ScoreRepositoryImpl();
    }

    @Test
    public void testSaveScore() {
        scoreRepository.saveScore(1, 100, User.generateUser(1234, new Random()));
    }

    @Test
    public void testHighestScore() {
        final int levelId = 1;
        final int score = 100;
        final int userId = 1234;
        scoreRepository.saveScore(levelId, score, User.generateUser(userId, new Random()));
        final List<Score> scores = scoreRepository.getHighestScore(levelId, 15);
        assertFalse(scores.isEmpty());
        assertEquals(1, scores.size());
        assertEquals(score, scores.get(0).getScoreValue());
        assertEquals(userId, scores.get(0).getUser().getUserId());
        assertEquals(levelId, scores.get(0).getLevel().getLevel());
    }

    @Test
    public void testHighestScoreEmpty() {
        List<Score> scores = scoreRepository.getHighestScore(1, 15);
        assertTrue(scores.isEmpty());
    }

    @Test
    public void testHighestScoreUserWithTwoScoreAndSameLevel() {
        final int levelId = 1;
        final int score1 = 100;
        final int score2 = 500;
        final int userId1 = 1234;
        final int userId2 = 9999;
        scoreRepository.saveScore(levelId, score1, User.generateUser(userId1, new Random()));
        scoreRepository.saveScore(levelId, score2, User.generateUser(userId2, new Random()));

        final List<Score> scores = scoreRepository.getHighestScore(levelId, 15);
        assertFalse(scores.isEmpty());
        assertEquals(2, scores.size());
        assertEquals(score2, scores.get(0).getScoreValue());
        assertEquals(userId2, scores.get(0).getUser().getUserId());
        assertEquals(levelId, scores.get(0).getLevel().getLevel());
        assertEquals(score1, scores.get(1).getScoreValue());
        assertEquals(userId1, scores.get(1).getUser().getUserId());
        assertEquals(levelId, scores.get(1).getLevel().getLevel());
    }

    @Test
    public void testHighestScoreWithMoreThan2Scores() {
        final int levelId = 1;
        final int score1 = 100;
        final int score2 = 500;
        final int score3 = 200;
        final int score4 = 80;
        final int userId1 = 1234;
        final int userId2 = 9999;
        final int userId3 = 5555;
        scoreRepository.saveScore(levelId, score1, User.generateUser(userId1, new Random()));
        scoreRepository.saveScore(levelId, score2, User.generateUser(userId2, new Random()));
        scoreRepository.saveScore(levelId, score3, User.generateUser(userId1, new Random()));
        scoreRepository.saveScore(levelId, score4, User.generateUser(userId3, new Random()));

        final List<Score> scores = scoreRepository.getHighestScore(levelId, 2);
        assertFalse(scores.isEmpty());
        assertEquals(2, scores.size());
        assertEquals(score2, scores.get(0).getScoreValue());
        assertEquals(userId2, scores.get(0).getUser().getUserId());
        assertEquals(levelId, scores.get(0).getLevel().getLevel());
        assertEquals(score3, scores.get(1).getScoreValue());
        assertEquals(userId1, scores.get(1).getUser().getUserId());
        assertEquals(levelId, scores.get(1).getLevel().getLevel());
    }
}
