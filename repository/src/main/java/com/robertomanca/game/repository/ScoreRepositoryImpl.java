package com.robertomanca.game.repository;

import com.robertomanca.game.model.Level;
import com.robertomanca.game.model.Score;
import com.robertomanca.game.model.User;
import com.robertomanca.game.repository.com.robertomanca.game.repository.model.ScoreDO;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class ScoreRepositoryImpl implements ScoreRepository {

    private static final BinaryOperator<ScoreDO> MERGER = (s1, s2) -> s1.compareTo(s2) >= 0 ? s1 : s2;
    private ConcurrentHashMap<Integer, List<ScoreDO>> scores;

    public ScoreRepositoryImpl() {
        scores = new ConcurrentHashMap<>();
    }

    @Override
    public void saveScore(final int levelId, final int score, final User user) {

        final ScoreDO scoreDO = new ScoreDO();
        scoreDO.setLevelId(levelId);
        scoreDO.setUserId(user.getUserId());
        scoreDO.setScore(score);

        scores.compute(levelId, (levId, scores) -> {
            List<ScoreDO> list = Optional.ofNullable(scores)
                    .orElse(new LinkedList<>());
            list.add(scoreDO);
            return list;
        });
    }

    //with millions of scores can be expensive
    @Override
    public List<Score> getHighestScore(final int levelId, final int maxResults) {
        return Optional.ofNullable(scores.get(levelId))
                .map(List::stream)
                .orElse(Stream.empty())
                .collect(Collectors.toConcurrentMap(ScoreDO::getUserId, Function.identity(), MERGER))
                .values()   //TODO check possible npe
                .stream()
                .sorted(Comparator.naturalOrder())
                .limit(maxResults)
                .map(convertToScore())
                .collect(Collectors.toList());
    }

    private Function<ScoreDO, Score> convertToScore() {
        return scoreDO -> {
            final Score score = new Score();
            final Level level = new Level();
            level.setLevel(scoreDO.getLevelId());
            score.setLevel(level);
            score.setScoreValue(scoreDO.getScore());
            // user info will be enriched in the use case
            final User user = new User();
            user.setUserId(scoreDO.getUserId());
            score.setUser(user);
            return score;
        };
    }
}
