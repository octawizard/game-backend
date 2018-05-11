package com.robertomanca.game.usecase;

import com.robertomanca.game.injector.InjectorManager;
import com.robertomanca.game.model.Score;
import com.robertomanca.game.repository.ScoreRepository;
import com.robertomanca.game.repository.UserRepository;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class GetHighScoreUseCaseImpl implements GetHighScoreUseCase {

    private ScoreRepository scoreRepository;
    private UserRepository userRepository;

    public GetHighScoreUseCaseImpl() {
        scoreRepository = InjectorManager.getInstance(ScoreRepository.class);
        userRepository = InjectorManager.getInstance(UserRepository.class);
    }

    @Override
    public List<Score> getHighestScore(final int levelId) {
        return scoreRepository.getHighestScore(levelId, 15)
                .stream()
                .map(enrichUser())
                .collect(Collectors.toList());
    }

    private UnaryOperator<Score> enrichUser() {
        return score -> {
            score.setUser(userRepository.getUser(score.getUser().getUserId()));
            return score;
        };
    }
}
