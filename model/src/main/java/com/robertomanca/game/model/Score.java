package com.robertomanca.game.model;

/**
 * Created by Roberto Manca on 10/05/2018.
 */
public class Score {

    private User user;
    private Level level;
    private int scoreValue;

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(final int scoreValue) {
        this.scoreValue = scoreValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Score score = (Score) o;

        if (scoreValue != score.scoreValue) {
            return false;
        }
        if (user != null ? !user.equals(score.user) : score.user != null) {
            return false;
        }
        return level != null ? level.equals(score.level) : score.level == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + scoreValue;
        return result;
    }
}
