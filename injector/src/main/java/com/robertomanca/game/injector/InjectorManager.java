package com.robertomanca.game.injector;

import com.robertomanca.game.repository.ScoreRepository;
import com.robertomanca.game.repository.ScoreRepositoryImpl;
import com.robertomanca.game.repository.SessionRepository;
import com.robertomanca.game.repository.SessionRepositoryImpl;
import com.robertomanca.game.repository.UserRepository;
import com.robertomanca.game.repository.UserRepositoryImpl;
import com.robertomanca.game.usecase.GetHighScoreUseCase;
import com.robertomanca.game.usecase.GetHighScoreUseCaseImpl;
import com.robertomanca.game.usecase.LoginUseCase;
import com.robertomanca.game.usecase.LoginUseCaseImpl;
import com.robertomanca.game.usecase.PostScoreUseCase;
import com.robertomanca.game.usecase.PostScoreUseCaseImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public enum InjectorManager implements InjectorConfigurator, InjectorProvider {
    INSTANCE;

    private static Map<Class, Object> singletons = new HashMap<>();
    private static Map<Class, Class> bindings = new HashMap<>();

    public void configureSingletonsAndBindings() {
        singletons.put(SessionRepository.class, new SessionRepositoryImpl());
        singletons.put(ScoreRepository.class, new ScoreRepositoryImpl());
        singletons.put(UserRepository.class, new UserRepositoryImpl());

        bindings.put(LoginUseCase.class, LoginUseCaseImpl.class);
        bindings.put(PostScoreUseCase.class, PostScoreUseCaseImpl.class);
        bindings.put(GetHighScoreUseCase.class, GetHighScoreUseCaseImpl.class);
    }

    public <T> T getInstance(Class<T> clazz) {
        try {
            return Optional.ofNullable(singletons.get(clazz))
                    .filter(clazz::isInstance)
                    .map(instance -> (T) instance)
                    .orElse((T) bindings.get(clazz).newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error instantiating instance of " + clazz, e);
        }
    }
}