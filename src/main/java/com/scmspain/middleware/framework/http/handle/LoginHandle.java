package com.scmspain.middleware.framework.http.handle;

import com.scmspain.middleware.framework.controller.LoginController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class LoginHandle implements HttpHandler {
    public final static String CONTEXT = "/app";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandle.class);

    private final LoginController loginController = new LoginController();
    //private final HttpHandler sessionHandler;

    /*public LoginHandler(HttpHandler sessionHandler) {

        this.sessionHandler = sessionHandler;
    }*/

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            this.handleThrowable(httpExchange);
        } catch (Exception exception) {
            LOGGER.error("LoginHandler error: ", exception);

            httpExchange.sendResponseHeaders(500, 0);
        } finally {
            httpExchange.close();
        }

    }

    protected void handleThrowable(HttpExchange httpExchange) throws IOException  {
        //sessionHandler.handle(httpExchange);

        loginController.handle(httpExchange);
    }
}
