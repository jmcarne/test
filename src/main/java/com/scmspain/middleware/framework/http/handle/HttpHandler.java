package com.scmspain.middleware.framework.http.handle;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by josep.carne on 05/02/2017.
 */
public interface HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException;
}
