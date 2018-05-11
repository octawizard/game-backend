package com.robertomanca.game.repository;

import com.robertomanca.game.model.Score;
import com.robertomanca.game.model.User;

import java.util.List;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public interface ScoreRepository {

    void saveScore(int levelId, int score, User user);

    List<Score> getHighestScore(int levelId, int maxResults);
}
