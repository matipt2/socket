package com.githubprojects.reactiveSockets.http;

import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;


public class AsyncHttpClient {

    public void start(int port){
        HttpClient httpClient =
                HttpClient.create()
                        .port(port)
                        .host("127.0.0.1");

        httpClient.post()
                .uri("/")
                .send(ByteBufFlux.fromString(Mono.just("hi!")))
                .response()
                .block();

    }
}
