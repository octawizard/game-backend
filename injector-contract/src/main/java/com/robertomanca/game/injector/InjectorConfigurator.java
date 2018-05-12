package com.robertomanca.game.injector;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public interface InjectorConfigurator {

   <T, U extends T> void configureSingletons(Class<T> clazz, U object );

   <T, U extends T> void configureBindings(Class<T> tClass, Class<U> uClass);
}
