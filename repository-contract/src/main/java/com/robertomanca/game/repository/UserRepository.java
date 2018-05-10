package com.robertomanca.game.repository;

import com.robertomanca.game.model.User;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public interface UserRepository {

    User getUser(int userId);
}
