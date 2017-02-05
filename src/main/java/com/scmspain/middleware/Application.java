package com.scmspain.middleware;

import com.scmspain.middleware.framework.http.handle.LoginHandle;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class Application {

    private static final Logger log = Logger.getLogger(Application.class.getName());
    private static final int port = 9000;


    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        HttpHandler handler = new HttpHandler() {
            public void handle(HttpExchange t) throws IOException {
                InputStream is = t.getRequestBody();
                while (is.read() != -1);
                t.sendResponseHeaders (404, -1);
                t.close();
            }
        };
        server.createContext(LoginHandle.CONTEXT, handler);
        server.setExecutor(null);

        server.start();
    }
}
