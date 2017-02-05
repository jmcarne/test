package com.scmspain.middleware.framework.http.session;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class ContextSession {

    public static void setSession(Session session) {

        ContextSessionLocal.set(session);
    }

    public static Session getSession() {

        return ContextSessionLocal.get();
    }

    private static final ThreadLocal<Session> ContextSessionLocal = new ThreadLocal<>();
}
