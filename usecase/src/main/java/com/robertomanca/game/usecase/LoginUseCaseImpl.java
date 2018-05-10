package com.robertomanca.game.usecase;

import com.robertomanca.game.model.Session;
import com.robertomanca.game.model.User;
import com.robertomanca.game.repository.SessionRepository;
import com.robertomanca.game.repository.UserRepository;

/**
 * Created by Roberto Manca on 10/05/2018.
 */
public class LoginUseCaseImpl implements  LoginUseCase {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    public Session getSession(final int userId) {
        final User user = userRepository.getUser(userId);
        return sessionRepository.createSession(user);
    }
}
