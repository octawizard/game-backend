package com.robertomanca.game.web.util;

import com.robertomanca.game.model.Score;

import java.util.List;

/**
 * Created by Roberto Manca on 13-May-18.
 */
public class ScoresCSVFormatter {

    public static String formatCSV(final List<Score> scores) {
        final StringBuilder builder = new StringBuilder();

        scores.forEach(score ->
                builder.append(score.getUser().getUserId())
                        .append('=')
                        .append(score.getScoreValue())
                        .append(',')
        );

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
