package com.robertomanca.game.usecase;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.model.exception.SessionExpiredException;
import com.robertomanca.game.model.exception.SessionNotFoundException;
import com.robertomanca.game.repository.ScoreRepository;
import com.robertomanca.game.repository.SessionRepository;
import com.robertomanca.game.repository.UserRepository;

import java.util.UUID;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class PostScoreUseCaseImpl implements PostScoreUseCase {

    private SessionRepository sessionRepository;
    private ScoreRepository scoreRepository;
    private UserRepository userRepository;

    public PostScoreUseCaseImpl() {
        sessionRepository = InjectorFactory.getInjectorProvider().getInstance(SessionRepository.class);
        userRepository = InjectorFactory.getInjectorProvider().getInstance(UserRepository.class);
        scoreRepository = InjectorFactory.getInjectorProvider().getInstance(ScoreRepository.class);
    }

    @Override
    public void postScore(final int levelId, final UUID sessionKey, final int score) throws SessionExpiredException, SessionNotFoundException {
        final Session session = sessionRepository.getSession(sessionKey).orElseThrow(SessionNotFoundException::new);
        final User user = userRepository.getUser(session.getUserId());
        scoreRepository.saveScore(levelId, score, user);
    }
}
