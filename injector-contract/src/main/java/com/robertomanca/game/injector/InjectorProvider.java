package com.robertomanca.game.injector;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public interface InjectorProvider {

    <T> T getInstance(Class<T> clazz);
}
