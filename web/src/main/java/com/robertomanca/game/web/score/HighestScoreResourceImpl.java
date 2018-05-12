package com.robertomanca.game.web.score;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.usecase.GetHighScoreUseCase;
import com.robertomanca.game.usecase.PostScoreUseCase;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public class HighestScoreResourceImpl implements HighestScoreResource {

    private GetHighScoreUseCase getHighScoreUseCase;

    public HighestScoreResourceImpl() {
        this.getHighScoreUseCase = InjectorFactory.getInjectorProvider().getInstance(GetHighScoreUseCase.class);
    }

    @Override
    public void process(final HttpExchange t) throws IOException {

    }
}
