package com.robertomanca.game.web.score;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.exception.SessionExpiredException;
import com.robertomanca.game.model.exception.SessionNotFoundException;
import com.robertomanca.game.usecase.PostScoreUseCase;
import com.robertomanca.game.web.util.BadParameterHandler;
import com.robertomanca.game.web.util.HttpRequestBodyReader;
import com.robertomanca.game.web.util.IntValidator;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public class PostScoreResourceImpl implements PostScoreResource, BadParameterHandler {

    private PostScoreUseCase postScoreUseCase;
    private HttpRequestBodyReader httpRequestBodyReader;

    public PostScoreResourceImpl() {
        this.postScoreUseCase = InjectorFactory.getInjectorProvider().getInstance(PostScoreUseCase.class);
        this.httpRequestBodyReader = InjectorFactory.getInjectorProvider().getInstance(HttpRequestBodyReader.class);
    }

    @Override
    public void process(final HttpExchange t) throws IOException {
        //extract parameters
        final String path = t.getRequestURI().getPath();
        final int beginIndex = path.indexOf('/') + 1;
        final String sLevelId = path.substring(beginIndex, path.indexOf('/', beginIndex));

        final String sUUID = t.getRequestURI().getQuery().split("=")[1];

        final String sScore = httpRequestBodyReader.read(t.getRequestBody());

        //do validation
        OptionalInt optLevelId = IntValidator.validate(sLevelId);
        if (!optLevelId.isPresent()) {
            handleBadParameterScenario(t, "The provided level id is not valid");
            return;
        }
        final UUID uuid;
        try {
            uuid = UUID.fromString(sUUID);
        } catch (IllegalArgumentException e) {
            handleBadParameterScenario(t, "The provided session key is not valid");
            return;
        }
        OptionalInt optScore = IntValidator.validate(sScore);
        if (!optScore.isPresent()) {
            handleBadParameterScenario(t, "The provided score is not valid");
            return;
        }

        // call use case
        try {
            postScoreUseCase.postScore(optLevelId.getAsInt(), uuid, optScore.getAsInt());
        } catch (SessionNotFoundException e) {
            handleBadParameterScenario(t, "No session found for key " + uuid.toString());
        } catch (SessionExpiredException e) {
            handleBadParameterScenario(t, "Session expired " + uuid.toString());
        }

        // setup the answer in ascii string
        String response = "";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
