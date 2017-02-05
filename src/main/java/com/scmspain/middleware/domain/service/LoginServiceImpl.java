package com.scmspain.middleware.domain.service;

import com.scmspain.middleware.domain.model.dao.UserDao;
import com.scmspain.middleware.domain.view.LoginFormImpl;
import com.scmspain.middleware.framework.http.session.ContextSession;
import com.scmspain.middleware.framework.http.session.Session;
import com.scmspain.middleware.framework.http.session.Sessions;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class LoginServiceImpl {
    private static final String COOKIE_HEADER = "Cookie";

    public boolean isValidUser(String user, String password) {
        final UserDao userDao = new UserDao();

        if (null != userDao.findByUserAndPassword(user, password)) {
            return true;
        } else {
            return false;
        }

    }

    public void processLogoutGet(HttpExchange httpExchange) throws IOException {
        final Headers headers = httpExchange.getRequestHeaders();
        final String cookieValue = headers.getFirst(COOKIE_HEADER);

        if (cookieValue != null) {
            final UUID uuid = UUID.fromString(cookieValue);
            Sessions.getInstance().removeSession(uuid);
        }

        httpExchange.sendResponseHeaders(200, 0);
    }

    public void processLoginGet(HttpExchange httpExchange) throws IOException {
        final String requestedURI = httpExchange.getRequestURI().toString();
        final Session session = ContextSession.getSession();
        final LoginFormImpl loginForm = new LoginFormImpl();

        String html = "";
        if (Sessions.getInstance().isValidSession()) {
            html = loginForm.doNoRequiredLogin();
            Sessions.getInstance().refreshSession(session.getUUID(), session.getUsername());
        } else {
            //html = loginForm.doRequiredLogin(requestedURI);
        }

        httpExchange.sendResponseHeaders(200, html.length());
        try (final OutputStream os = httpExchange.getResponseBody()) {
            os.write(html.getBytes());
        }

    }

    public void processLoginPost(HttpExchange httpExchange) throws IOException {
        final Session session = ContextSession.getSession();

        if (!Sessions.getInstance().isValidSession()) {
            String body = this.getBody(httpExchange);
            String [] formData = body.split("&");
            if (formData.length == 2) {
                String username = formData[0].split("=")[1];
                String password = formData[1].split("=")[1];

                LoginServiceImpl loginService = new LoginServiceImpl();
                if (loginService.isValidUser(username, password)) {
                    UUID uuid = UUID.randomUUID();
                    this.setCookieHeader(httpExchange, uuid.toString());
                    Sessions.getInstance().refreshSession(uuid, username);
                    this.doRedirect(httpExchange);
                } else {

                    final UnAuthenticated view = new UnAuthenticated();
                    String html = view.doAuthenticated();
                    httpExchange.sendResponseHeaders(401, html.length());
                    try (final OutputStream os = httpExchange.getResponseBody()) {
                        os.write(html.getBytes());
                    }
                }
            }
        } else {
            Sessions.getInstance().refreshSession(session.getUUID(), session.getUsername());
            doRedirect(httpExchange);
        }
    }

    protected String getBody (HttpExchange httpExchange) throws IOException {
        try(final InputStream inputStream = httpExchange.getRequestBody();
            final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream()) {

            final int bufferSize = 1024;
            final byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return new String(byteBuffer.toByteArray(), Charset.forName("UTF-8"));
        }
    }

    protected void setCookieHeader(HttpExchange httpExchange, String UUIDString) {
        Headers headers = httpExchange.getResponseHeaders();

        headers.remove("Set-Cookie");
        headers.set("Set-Cookie", UUIDString + "; path=/");
    }

    protected void doRedirect(HttpExchange httpExchange) throws IOException {
        String requestURIString = httpExchange.getRequestURI().toString();
        String[] urls = requestURIString.split("serviceName=");
        String serviceName = "";
        if (urls.length == 2) {
            serviceName = urls[1];
        }

        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.add("Location", serviceName);
        httpExchange.sendResponseHeaders(302, 0);
    }
}
