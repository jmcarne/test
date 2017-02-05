package com.scmspain.middleware.framework.http.session;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by josep.carne on 05/02/2017.
 */
public class Session {
    private final UUID uuid;
    private final String username;
    private final LocalDateTime lastSessionTime;

    public Session(UUID uuid, String username, LocalDateTime lastSessionTime) {
        this.uuid = uuid;
        this.username = username;
        this.lastSessionTime = lastSessionTime;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }
    public LocalDateTime getLastSessionTime() {
        return lastSessionTime;
    }
}
