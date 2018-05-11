package com.robertomanca.game.repository;

import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.model.exception.SessionExpiredException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public interface SessionRepository {

    Session createSession(User user, LocalDateTime localDateTime);

    Optional<Session> getSession(UUID key) throws SessionExpiredException;
}
