package com.scmspain.middleware.framework.http.session;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by josep.carne on 05/02/2017.
 */


public class Sessions {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sessions.class);

    private final Long sessionTimeout;

    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

    private Sessions() {
        final Properties properties = this.loadProperties();

        sessionTimeout = Long.valueOf(properties.getProperty("session.expire"));
    }

    private static class SessionsContextHolder {
        private static final Sessions INSTANCE = new Sessions();
    }

    public static Sessions getInstance() {
        return SessionsContextHolder.INSTANCE;
    }

    public Session getSession(UUID uuid) {
        return sessions.get(uuid);
    }

    public void refreshSession(UUID uuid, String username) {
        final Session newSession =
                new Session(uuid, username, LocalDateTime.now());
        sessions.put(uuid, newSession);
    }

    public void removeSession(UUID uuid) {
        sessions.remove(uuid);
    }

    public boolean isValidSession() {
        final Session sessionInfo = ContextSession.getSession();

        boolean isValid = false;

        if (sessionInfo != null) {
            final LocalDateTime currentDateTime = LocalDateTime.now();
            if (sessionInfo
                    .getLastSessionTime().plusMinutes(sessionTimeout).compareTo(currentDateTime) > 0) {
                isValid = true;
            } else {
                sessions.remove(sessionInfo.getUUID());
            }
        }

        return isValid;
    }

    private Properties loadProperties() {
        final Properties properties = new Properties();

        try(final InputStream in = this.getClass().getResourceAsStream("/prueba.properties")) {
            properties.load(in);
        } catch (IOException exception) {
            LOGGER.error("Load properties file error: ", exception);

            throw new IllegalStateException("Load properties file error", exception);
        }

        return properties;
    }
}

