package com.robertomanca.game.starter;

import com.robertomanca.game.injector.InjectorConfigurator;
import com.robertomanca.game.injector.InjectorFactory;
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
import com.robertomanca.game.web.GameBackendHandler;
import com.robertomanca.game.web.login.LoginResource;
import com.robertomanca.game.web.login.LoginResourceImpl;
import com.robertomanca.game.web.score.HighestScoreResource;
import com.robertomanca.game.web.score.HighestScoreResourceImpl;
import com.robertomanca.game.web.score.PostScoreResource;
import com.robertomanca.game.web.score.PostScoreResourceImpl;
import com.robertomanca.game.web.util.HttpRequestBodyReader;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by Roberto Manca on 11-May-18.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // configure singletons for injection
        configureResourcesBindings();
        // start server
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new GameBackendHandler());
        server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        server.start();
    }

    private static void configureResourcesBindings() {
        final InjectorConfigurator injectorConfigurator = InjectorFactory.getInjectorConfigurator();
        injectorConfigurator.configureBinding(LoginResource.class, LoginResourceImpl.class);
        injectorConfigurator.configureBinding(PostScoreResource.class, PostScoreResourceImpl.class);
        injectorConfigurator.configureBinding(HighestScoreResource.class, HighestScoreResourceImpl.class);

        // use cases
        injectorConfigurator.configureBinding(LoginUseCase.class, LoginUseCaseImpl.class);
        injectorConfigurator.configureBinding(PostScoreUseCase.class, PostScoreUseCaseImpl.class);
        injectorConfigurator.configureBinding(GetHighScoreUseCase.class, GetHighScoreUseCaseImpl.class);

        // repositories singletons
        injectorConfigurator.configureSingleton(UserRepository.class, new UserRepositoryImpl());
        injectorConfigurator.configureSingleton(ScoreRepository.class, new ScoreRepositoryImpl());
        injectorConfigurator.configureSingleton(SessionRepository.class, new SessionRepositoryImpl());

        // utils singleton
        injectorConfigurator.configureSingleton(HttpRequestBodyReader.class, new HttpRequestBodyReader());
    }
}
