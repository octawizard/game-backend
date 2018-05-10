package com.robertomanca.game.repository;

import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.model.exception.SessionExpiredException;
import com.robertomanca.game.repository.com.robertomanca.game.repository.model.SessionDO;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public class SessionRepositoryImpl implements SessionRepository {

    private ConcurrentHashMap<UUID, SessionDO> sessions;

    public SessionRepositoryImpl() {
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public Session createSession(final User user) {
        final UUID uuid = UUID.randomUUID();
        final SessionDO sessionDO = createSessionDO(uuid);
        sessions.put(uuid, sessionDO);
        return convertToSession(sessionDO);
    }

    @Override
    public Optional<Session> getSession(final UUID key) throws SessionExpiredException {

        Optional<SessionDO> optionalSessionDO = Optional.ofNullable(sessions.get(key));

        if (!optionalSessionDO.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(
                optionalSessionDO.filter(SessionDO::isNotExpired)
                        .orElseThrow(SessionExpiredException::new))
                .map(this::convertToSession);
    }

    private Session convertToSession(final SessionDO sessionDO) {
        final Session session = new Session();
        session.setKey(sessionDO.getKey());
        return session;
    }

    private SessionDO createSessionDO(final UUID uuid) {
        SessionDO sessionDO = new SessionDO();
        sessionDO.setKey(uuid);
        sessionDO.setCreationDateTime(LocalDateTime.now());
        return sessionDO;
    }
}
