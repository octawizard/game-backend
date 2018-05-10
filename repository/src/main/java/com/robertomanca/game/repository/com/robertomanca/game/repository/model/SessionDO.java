package com.robertomanca.game.repository.com.robertomanca.game.repository.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public class SessionDO {

    private UUID key;
    private LocalDateTime creationDateTime;

    public UUID getKey() {
        return key;
    }

    public void setKey(final UUID key) {
        this.key = key;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(final LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public boolean isNotExpired() {
        return LocalDateTime.now().isBefore(this.creationDateTime.plusMinutes(10));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SessionDO sessionDO = (SessionDO) o;

        if (key != null ? !key.equals(sessionDO.key) : sessionDO.key != null) {
            return false;
        }
        return creationDateTime != null ? creationDateTime.equals(sessionDO.creationDateTime) : sessionDO.creationDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (creationDateTime != null ? creationDateTime.hashCode() : 0);
        return result;
    }
}
