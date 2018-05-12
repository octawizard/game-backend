package com.robertomanca.game.web.login;

import com.robertomanca.game.injector.InjectorFactory;
import com.robertomanca.game.model.Session;
import com.robertomanca.game.usecase.LoginUseCase;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.OptionalInt;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class LoginResourceImpl implements LoginResource {

    private LoginUseCase loginUseCase;

    public LoginResourceImpl() {
        this.loginUseCase = InjectorFactory.getInjectorProvider().getInstance(LoginUseCase.class);
    }

    /*
    The typical life-cycle of a HttpExchange is shown in the sequence below.

getRequestMethod() to determine the command
getRequestHeaders() to examine the request headers (if needed)
getRequestBody() returns a InputStream for reading the request body. After reading the request body, the stream is close.
getResponseHeaders() to set any response headers, except content-length
sendResponseHeaders(int,long) to send the response headers. Must be called before next step.
getResponseBody() to get a OutputStream to send the response body. When the response body has been written, the stream must be closed to terminate the exchange.
     */

    @Override
    public void login(final HttpExchange t) throws IOException {
        //extract parameters
        final String path = t.getRequestURI().getPath();
        final int beginIndex = path.indexOf('/') + 1;
        String sUserId = path.substring(beginIndex, path.indexOf('/', beginIndex));

        //do validation
        OptionalInt optUserId = validateUserId(sUserId);
        if (!optUserId.isPresent()) {
            handleBadParameterScenario();
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

    private void handleBadParameterScenario() {
        //TODO implement
    }

    private OptionalInt validateUserId(final String sUserId) {
        try {
            return OptionalInt.of(Integer.valueOf(sUserId));
        } catch (NumberFormatException e) {
            return OptionalInt.empty();
        }
    }
}
