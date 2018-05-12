package com.robertomanca.game.web.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public interface BadParameterHandler {

    default void handleBadParameterScenario(final HttpExchange t, final String message) throws IOException {

        t.sendResponseHeaders(400, message.length());
        OutputStream os = t.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }
}
