package com.githubprojects.reactiveSockets.http;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;


public class AsyncHttpClient {


    public void start(int port){
        DisposableServer httpServer = HttpServer.create()
                .host("127.0.0.1")
                .port(port)
                .bindNow();

        httpServer.onDispose()
                .block();

    }
}
