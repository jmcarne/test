package com.scmspain.middleware.framework.controller;

import com.scmspain.middleware.domain.view.PageImpl;
import com.scmspain.middleware.framework.http.session.ContextSession;
import com.scmspain.middleware.framework.http.session.Session;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by josep.carne on 08/02/2017.
 */
public class PagesController implements Controller {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        final String requestMethod = httpExchange.getRequestMethod();

        switch (requestMethod) {
            case "GET":
                this.processPages(httpExchange);
                break;
            default:
                httpExchange.sendResponseHeaders(404, 0);
                break;
        }

    }

    protected void processPages(HttpExchange httpExchange) throws IOException {
        final String requestedURI = httpExchange.getRequestURI().toString();
        final PageImpl pageImpl = new PageImpl();

        int responseStatus = 200;
        String html = "";
        switch (requestedURI) {
            case "/app/pages/page1.html":
                html = pageImpl.doPage(1, getSafeUserName());
                break;
            case "/app/pages/page2.html":
                html = pageImpl.doPage(2, getSafeUserName());
                break;
            case "/app/pages/page3.html":
                html = pageImpl.doPage(3, getSafeUserName());
                break;
            default:
                responseStatus = 404;
                break;
        }

        httpExchange.sendResponseHeaders(responseStatus, html.length());
        try (final OutputStream os = httpExchange.getResponseBody()) {
            os.write(html.getBytes());
        }
    }

    protected String getSafeUserName() {
        Session session = ContextSession.getSession();
        String userName = "";

        if (session != null) {
            userName = session.getUsername();
        }

        return userName;
    }
}
