package com.robertomanca.game.usecase;

import com.robertomanca.game.model.Score;

import java.util.List;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public interface GetHighScoreUseCase {

    List<Score> getHighestScore(int levelId);
}
