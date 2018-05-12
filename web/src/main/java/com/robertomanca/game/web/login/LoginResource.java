package com.robertomanca.game.web.login;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public interface LoginResource {
    void login(HttpExchange t) throws IOException;
}
