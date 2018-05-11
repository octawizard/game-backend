package com.robertomanca.game.usecase;

import com.robertomanca.game.model.exception.SessionExpiredException;
import com.robertomanca.game.model.exception.SessionNotFoundException;

import java.util.UUID;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public interface PostScoreUseCase {

    void postScore(int levelId, UUID sessionKey, int score) throws SessionExpiredException, SessionNotFoundException;
}
