package com.robertomanca.game.repository;

import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public interface SessionRepository {

    Session createSession(User user);
}
