package com.robertomanca.game.web.login;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Session;
import com.robertomanca.game.usecase.LoginUseCase;
import com.robertomanca.game.web.util.BadParameterHandler;
import com.robertomanca.game.web.util.IntValidator;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.OptionalInt;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class LoginResourceImpl implements LoginResource, BadParameterHandler {

    private LoginUseCase loginUseCase;

    public LoginResourceImpl() {
        this.loginUseCase = InjectorFactory.getInjectorProvider().getInstance(LoginUseCase.class);
    }

    @Override
    public void process(final HttpExchange t) throws IOException {
        //extract parameters
        final String path = t.getRequestURI().getPath();
        final int beginIndex = path.indexOf('/') + 1;
        String sUserId = path.substring(beginIndex, path.indexOf('/', beginIndex));

        //do validation
        OptionalInt optUserId = IntValidator.validate(sUserId);
        if (!optUserId.isPresent()) {
            handleBadParameterScenario(t, "The provided user id is not valid");
            return;
        }

        // call use case
        final Session session = loginUseCase.getSession(Integer.valueOf(sUserId));

        // setup the answer in ascii string
        String response = session.getKey().toString();
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
