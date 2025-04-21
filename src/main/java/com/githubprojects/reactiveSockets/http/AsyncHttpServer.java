package com.githubprojects.reactiveSockets.http;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class AsyncHttpServer {

    public void start(int port){
        DisposableServer httpServer =
                HttpServer.create()
                        .host("127.0.0.1")
                        .port(port)
                        .route(routes -> routes.post("/", ((request, response) ->
                                request.receive()
                                        .asString()
                                        .doOnNext(message -> System.out.println(request.requestHeaders()))
                                        .then(response.status(200).send().then()))
                        ))
                        .bindNow();

        httpServer.onDispose()
                .block();

    }

}
