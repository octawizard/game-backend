package com.robertomanca.game.model;

import java.util.UUID;

/**
 * Created by Roberto Manca on 10/05/2018.
 */
public class Session {

    private UUID key;
    private int userId;

    public UUID getKey() {
        return key;
    }

    public void setKey(final UUID key) {
        this.key = key;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Session session = (Session) o;

        if (userId != session.userId) {
            return false;
        }
        return key != null ? key.equals(session.key) : session.key == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + userId;
        return result;
    }
}
