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
        injectorConfigurator.configureBindings(LoginResource.class, LoginResourceImpl.class);
        // TODO add the other resources lataer

        // use cases
        injectorConfigurator.configureBindings(LoginUseCase.class, LoginUseCaseImpl.class);
        injectorConfigurator.configureBindings(PostScoreUseCase.class, PostScoreUseCaseImpl.class);
        injectorConfigurator.configureBindings(GetHighScoreUseCase.class, GetHighScoreUseCaseImpl.class);

        // repositories singletons
        injectorConfigurator.configureSingletons(UserRepository.class, new UserRepositoryImpl());
        injectorConfigurator.configureSingletons(ScoreRepository.class, new ScoreRepositoryImpl());
        injectorConfigurator.configureSingletons(SessionRepository.class, new SessionRepositoryImpl());
    }

//    static class MyHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange t) throws IOException {
//            String response = "This is the 200 response";
//            response += t.getRequestMethod();
//            t.sendResponseHeaders(200, response.length());
//            OutputStream os = t.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
//        }
//    }
//
//    static class NotFoundHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange t) throws IOException {
//            String response = t.getRequestURI().toASCIIString();
//            response += t.getRequestMethod();
//            response += " The request resource is not found";
//            t.sendResponseHeaders(404, response.length());
//            OutputStream os = t.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
//        }
//    }


}
