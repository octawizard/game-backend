package com.robertomanca.game.model;

import java.util.UUID;

/**
 * Created by Roberto Manca on 10/05/2018.
 */
public class Session {

    private UUID key;

    public UUID getKey() {
        return key;
    }

    public void setKey(final UUID key) {
        this.key = key;
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

        return key != null ? key.equals(session.key) : session.key == null;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }
}
