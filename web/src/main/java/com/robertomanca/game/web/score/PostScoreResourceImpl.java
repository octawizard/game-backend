package com.robertomanca.game.web.score;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.usecase.PostScoreUseCase;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public class PostScoreResourceImpl implements PostScoreResource {

    private PostScoreUseCase postScoreUseCase;

    public PostScoreResourceImpl() {
        this.postScoreUseCase = InjectorFactory.getInjectorProvider().getInstance(PostScoreUseCase.class);
    }

    @Override
    public void process(final HttpExchange t) throws IOException {

    }
}
