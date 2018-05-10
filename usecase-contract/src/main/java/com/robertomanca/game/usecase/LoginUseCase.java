package com.robertomanca.game.usecase;

import com.robertomanca.game.model.Session;

/**
 * Created by Roberto Manca on 10-May-18.
 */
public interface LoginUseCase {

    Session getSession(int userId);
}
