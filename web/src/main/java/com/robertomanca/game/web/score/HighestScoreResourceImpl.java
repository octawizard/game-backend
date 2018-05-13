package com.robertomanca.game.web.score;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Score;
import com.robertomanca.game.usecase.GetHighScoreUseCase;
import com.robertomanca.game.web.util.BadParameterHandler;
import com.robertomanca.game.web.util.IntValidator;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.OptionalInt;

import static com.robertomanca.game.web.util.ScoresCSVFormatter.formatCSV;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public class HighestScoreResourceImpl implements HighestScoreResource, BadParameterHandler {

    private GetHighScoreUseCase getHighScoreUseCase;

    public HighestScoreResourceImpl() {
        this.getHighScoreUseCase = InjectorFactory.getInjectorProvider().getInstance(GetHighScoreUseCase.class);
    }

    @Override
    public void process(final HttpExchange t) throws IOException {
        //extract parameters
        final String path = t.getRequestURI().getPath();
        final int beginIndex = path.indexOf('/') + 1;
        String sLevelId = path.substring(beginIndex, path.indexOf('/', beginIndex));

        //do validation
        OptionalInt optLevelId = IntValidator.validate(sLevelId);
        if (!optLevelId.isPresent()) {
            handleBadParameterScenario(t, "The provided level id is not valid");
            return;
        }

        // call use case
        final List<Score> highestScores = getHighScoreUseCase.getHighestScore(optLevelId.getAsInt());

        // setup the answer in ascii string
        final String response = formatCSV(highestScores);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
