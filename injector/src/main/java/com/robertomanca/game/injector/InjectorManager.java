package com.robertomanca.game.injector;

import com.robertomanca.game.repository.ScoreRepository;
import com.robertomanca.game.repository.ScoreRepositoryImpl;
import com.robertomanca.game.repository.SessionRepository;
import com.robertomanca.game.repository.SessionRepositoryImpl;
import com.robertomanca.game.repository.UserRepository;
import com.robertomanca.game.repository.UserRepositoryImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public enum InjectorManager implements InjectorConfigurator, InjectorProvider {
    INSTANCE;

    private static Map<Class, Object> singletons = new HashMap<>();

    public void configureSingletons() {
        singletons.put(SessionRepository.class, new SessionRepositoryImpl());
        singletons.put(ScoreRepository.class, new ScoreRepositoryImpl());
        singletons.put(UserRepository.class, new UserRepositoryImpl());
    }

    public <T> T getInstance(Class<T> clazz) {
        try {
            return Optional.ofNullable(singletons.get(clazz))
                    .filter(clazz::isInstance)
                    .map(instance -> (T) instance)
                    .orElse(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}