package com.robertomanca.game.repository;

import com.robertomanca.game.model.Score;
import com.robertomanca.game.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        final int score = 100;
        final int levelId = 1;
        final int userId = 1234;
        scoreRepository.saveScore(levelId, score, User.generateUser(userId, new Random()));

        final List<Score> highestScore = scoreRepository.getHighestScore(levelId, 10);
        assertEquals(1, highestScore.size());
        assertEquals(score, highestScore.get(0).getScoreValue());
        assertEquals(userId, highestScore.get(0).getUser().getUserId());
        assertEquals(levelId, highestScore.get(0).getLevel().getLevel());
    }

    @Test
    public void testSaveScoreTwoConcurrentOnTheSameLevel() throws InterruptedException {
        final int score1 = 100;
        final int score2 = 1000;
        final int levelId = 1;
        final int userId1 = 1234;
        final int userId2 = 9999;

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> scoreRepository.saveScore(levelId, score1, User.generateUser(userId1, new Random())));
        executorService.submit(() -> scoreRepository.saveScore(levelId, score2, User.generateUser(userId2, new Random())));

        Thread.sleep(200);

        final List<Score> highestScore = scoreRepository.getHighestScore(levelId, 10);
        assertEquals(2, highestScore.size());
        assertEquals(score2, highestScore.get(0).getScoreValue());
        assertEquals(userId2, highestScore.get(0).getUser().getUserId());
        assertEquals(levelId, highestScore.get(0).getLevel().getLevel());
        assertEquals(score1, highestScore.get(1).getScoreValue());
        assertEquals(userId1, highestScore.get(1).getUser().getUserId());
        assertEquals(levelId, highestScore.get(1).getLevel().getLevel());
    }

    @Test
    public void testSaveScoreTwoConcurrentOnDifferentLevel() throws InterruptedException {
        final int score1 = 100;
        final int score2 = 1000;
        final int levelId1 = 1;
        final int levelId2 = 2;
        final int userId1 = 1234;
        final int userId2 = 9999;

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> scoreRepository.saveScore(levelId1, score1, User.generateUser(userId1, new Random())));
        executorService.submit(() -> scoreRepository.saveScore(levelId2, score2, User.generateUser(userId2, new Random())));

        Thread.sleep(200);

        List<Score> highestScore = scoreRepository.getHighestScore(levelId1, 10);
        assertEquals(1, highestScore.size());
        assertEquals(score1, highestScore.get(0).getScoreValue());
        assertEquals(userId1, highestScore.get(0).getUser().getUserId());
        assertEquals(levelId1, highestScore.get(0).getLevel().getLevel());

        highestScore = scoreRepository.getHighestScore(levelId2, 10);
        assertEquals(score2, highestScore.get(0).getScoreValue());
        assertEquals(userId2, highestScore.get(0).getUser().getUserId());
        assertEquals(levelId2, highestScore.get(0).getLevel().getLevel());
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
