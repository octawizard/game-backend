package com.robertomanca.game.web;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.web.login.LoginResource;
import com.robertomanca.game.web.score.HighestScoreResource;
import com.robertomanca.game.web.score.PostScoreResource;
import com.robertomanca.game.web.util.NotFoundHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class GameBackendHandler implements HttpHandler, NotFoundHandler {

    private static final String GET = "GET";
    private static final String POST = "POST";

    private static final String LOGIN_REGEX = "/\\d+/login";
    private static final String HIGH_SCORE_LIST_REGEX = "/\\d+/highscorelist";
    private static final String SAVE_SCORE_REGEX = "/\\d+/score\\?sessionkey=.*";

    private final LoginResource loginResource;
    private final PostScoreResource postScoreResource;
    private final HighestScoreResource highestScoreResource;

    public GameBackendHandler() {
        loginResource = InjectorFactory.getInjectorProvider().getInstance(LoginResource.class);
        postScoreResource = InjectorFactory.getInjectorProvider().getInstance(PostScoreResource.class);
        highestScoreResource = InjectorFactory.getInjectorProvider().getInstance(HighestScoreResource.class);
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        redirect(t);
    }

    private void redirect(final HttpExchange t) throws IOException {
        final String uri = t.getRequestURI().toASCIIString();
        final String httpMethod = t.getRequestMethod();
        switch (httpMethod) {
            case GET:
                redirect(uri, t);
                break;
            case POST:
                postUserScore(uri, t);
                break;
        }
    }

    private void redirect(final String uri, final HttpExchange t) throws IOException {
        if (uri.matches(LOGIN_REGEX)) {
            loginResource.process(t);
        }
        if (uri.matches(HIGH_SCORE_LIST_REGEX)){
            highestScoreResource.process(t);
        } else {
            handleNotFoundScenario(t);
        }
    }

    private void postUserScore(final String uri, final HttpExchange t) throws IOException {
        if (uri.matches(SAVE_SCORE_REGEX)) {
            postScoreResource.process(t);
        } else {
            handleNotFoundScenario(t);
        }
    }
}
