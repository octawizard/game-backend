package com.robertomanca.game.repository.com.robertomanca.game.repository.model;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class ScoreDO implements Comparable<ScoreDO> {

    private int levelId;
    private int userId;
    private int score;

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(final int levelId) {
        this.levelId = levelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ScoreDO scoreDO = (ScoreDO) o;

        if (levelId != scoreDO.levelId) {
            return false;
        }
        if (userId != scoreDO.userId) {
            return false;
        }
        return score == scoreDO.score;
    }

    @Override
    public int hashCode() {
        int result = levelId;
        result = 31 * result + userId;
        result = 31 * result + score;
        return result;
    }

    @Override
    public int compareTo(final ScoreDO o) {
        return this.getScore() - o.getScore();
    }
}
