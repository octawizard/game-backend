package com.robertomanca.game.injector;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public final class InjectorFactory {

    private InjectorFactory() {
        throw new AssertionError("no instance of this for you");
    }

    public static InjectorProvider getInjectorProvider() {
        return InjectorManager.INSTANCE;
    }

    public static InjectorConfigurator getInjectorConfigurator() {
        return InjectorManager.INSTANCE;
    }
}
