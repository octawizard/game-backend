package com.robertomanca.game.injector;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public enum InjectorManager implements InjectorConfigurator, InjectorProvider {
    INSTANCE;

    private static Map<Class, Object> singletons = new HashMap<>();
    private static Map<Class, Class> bindings = new HashMap<>();

    public <T, U extends T> void configureSingletons(Class<T> clazz, U object) {
        singletons.put(clazz, object);
    }

    public <T, U extends T> void configureBindings(Class<T> tClass, Class<U> uClass) {
        bindings.put(tClass, uClass);
    }

    public <T> T getInstance(Class<T> clazz) {
        return Optional.ofNullable(singletons.get(clazz))
                .filter(clazz::isInstance)
                .map(instance -> (T) instance)
                .orElse((T) Optional.ofNullable(bindings.get(clazz))
                        .map(getNewInstance())
                        .orElse(null));
    }

    private Function<Class, Object> getNewInstance() {
        return cla -> {
            try {
                return cla.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
        };
    }
}