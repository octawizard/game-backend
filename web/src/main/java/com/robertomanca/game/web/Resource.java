package com.robertomanca.game.web;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by Roberto Manca on 12-May-18.
 */
public interface Resource {

    void process(HttpExchange t) throws IOException;
}
