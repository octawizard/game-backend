package com.robertomanca.game.web.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Roberto Manca on 13-May-18.
 */
public interface NotFoundHandler {

    default void handleNotFoundScenario(final HttpExchange t) throws IOException {
        String response = "The request resource is not found";
        t.sendResponseHeaders(404, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
